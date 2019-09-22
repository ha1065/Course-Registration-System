import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.io.*;
import java.io.File;
public class CRS {

	public static void main(String args[]) {
		
		ArrayList<Course> Courselist = new ArrayList<>();
		ArrayList<Student> Studentlist = new ArrayList<>();
		
		
		//Check if serialization has happened or not already, if not read from original CSV file	
		if (new File("Courses.ser").exists() == false) {
		    
		    try (BufferedReader br = new BufferedReader(new FileReader("MyUniversityCourses.csv"))) {
		        
		        br.readLine();
		        String line1 = null;
		    	while ((line1 = br.readLine()) != null) {
		    		
			        String[] info = line1.split(",");
			        
			        Courselist.add(new Course(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]) , new ArrayList<String>(Arrays.asList(info[4].split(","))) , info[5] , Integer.parseInt(info[6]) , info[7] ));
			        
		        }
		    
		    
		    }
		
		     catch(FileNotFoundException ioe) {
			    ioe.printStackTrace();
			    return;
		    }
		
		    catch(IOException ioe) {
		       ioe.printStackTrace();
		       return;
		    }
		}
		
		
		
		
		  
			
			
			
			//otherwise deserialize and read from ser files
		    else {
			
			  try{
				  //FileInputSystem recieves bytes from a file
			     FileInputStream fis = new FileInputStream("Courses.ser");
			     FileInputStream fis_1 = new FileInputStream("Students.ser");
			      
			      //ObjectInputStream does the deserialization-- it reconstructs the data into an object
			     ObjectInputStream ois = new ObjectInputStream(fis);
			     ObjectInputStream ois_1 = new ObjectInputStream(fis_1);
			      
			      
			     try{
			    	 while(true) {
			    		 Courselist.add((Course)ois.readObject());
			    		 
			    	 }
			     }
			     
			     
			     catch(EOFException e) {
			    	 
			     }
			     
			     try{
			    	 while(true) {
			    		 Studentlist.add((Student)ois_1.readObject());
			    		 
			    	 }
			     }
			     
			     
			     catch(EOFException e) {
			    	 
			     }
			     
			     ois.close();
			     ois_1.close();
			     fis.close();
			     fis_1.close();
			     }
			  catch(IOException ioe) {
			     ioe.printStackTrace();
			     return;
			  }
		      catch(ClassNotFoundException cnfe) {
			     cnfe.printStackTrace();
			     return;
			  }
		

		   }
		//an initial user object to make sure "welcome" message is displayed to all who login
		User inital_user = new User();
		Scanner in = new Scanner(System.in);
		System.out.print("If you are an Admin enter 0 , if you are a Student enter 1 : ");
		if(in.nextInt() == 0) {
		 while(true) {
			System.out.println("Enter Username :");
			String username = in.next();
			System.out.println("Enter Password : ");
			String pword = in.next();
		   if (username.equals("Admin") && pword.equals("Admin001")){
			   
			    //Admin's name need not be known
				Admin only_admin = new Admin(username , pword , null , null);
				
				inital_user.displayMenu();
				
			  while(true){
				
				System.out.println();  
				only_admin.displayMenu();
				System.out.println();  
				
				
				switch(in.nextInt()) {
				
				case 1 : only_admin.Createcourse(Courselist);break;
				case 2 : only_admin.deleteCourse(Courselist);break;	
				case 3 : only_admin.editCourse(Courselist);break;
				case 4 : only_admin.displayInfo(Courselist);break;
				case 5 : only_admin.registerStudent(Studentlist);break;
				case 6 : only_admin.displayInfo(Courselist, Studentlist);break;  
				case 7 : only_admin.viewFullCourses(Courselist);break;
				case 8 : try{
					     only_admin.writeFullCourses(Courselist) ;
				         }
				         catch(FileNotFoundException FileEx){
				        	 
				         }
				         break;
				case 9 : only_admin.viewStudents(Courselist);break;
				case 10: only_admin.viewCoursesOfaStudent(Courselist);break;
				case 11 : only_admin.sortCourses(Courselist);break;
				case 12 : only_admin.viewCourses(Courselist);break;
				case 0 : only_admin.Serialize(Courselist,Studentlist);
					
				}
			  }
		   }
				
			
			
				
		   else {
				
				System.out.println("Wrong username or password entered, enter 0 to exit or 1 to try again");
				if(in.nextInt() == 1) {
					continue;
				}
				else {
					System.exit(0);
				}
			}
			
		 }	
	   }
		
		else {
		  while(true) {
			  
		  
			System.out.println("Please enter your first name: ");
			String firstName = in.next();
			System.out.println("Please enter your last name : ");
			String lastName = in.next();
			for(int i = 0; i < Studentlist.size(); i++) {
				
				if(firstName.equals(Studentlist.get(i).FirstName) && lastName.equals(Studentlist.get(i).LastName)){
					System.out.println("Is this your first time accessing this system ? Type 1 for yes and 0 for no ");
					if(in.nextInt() == 1) {
						//check if username entered is unique or not
						String temp_user;
						do{
							System.out.println("Create username : ");
							temp_user = in.next();
							
							if(!Studentlist.get(i).isUnique(Studentlist, temp_user)){
								System.out.println("Username already taken. Try again");
								
							}
							else {
								break;
							}
							
							
						}while(!Studentlist.get(i).isUnique(Studentlist, temp_user));
						
						Studentlist.get(i).username = temp_user;
						System.out.println("Create passowrd : ");
						Studentlist.get(i).password = in.next();
						System.out.println("Please login using username and password you created");
						System.out.println();
					    
						
						  System.out.println("Username : ");
						  String username = in.next();
						  System.out.println("Password : ");
						  String password = in.next();
                          if(username.equals(Studentlist.get(i).username) && password.equals(Studentlist.get(i).password)) {
                        	 inital_user.displayMenu();
                        	while(true) {  
                        		
                        	  System.out.println();	
                        	  Studentlist.get(i).displayMenu();
                              switch(in.nextInt()) {
                            
                              case 1 : Studentlist.get(i).viewCourses(Courselist);break;
                              case 2 : Studentlist.get(i).viewNotFullCourses(Courselist);break;
                              case 3 : Studentlist.get(i).self_register(Courselist);break;
                              case 4 : Studentlist.get(i).withdraw(Courselist, Studentlist.get(i).fullName);break;
                              case 5 : Studentlist.get(i).coursesRegistered(Courselist);break;
                              case 0 : Studentlist.get(i).Serialize(Courselist,Studentlist);break;
                              }
                        	}
                          }
                          else {
                        	
                        	  System.out.println("Wrong username or password, try again");
                        	  continue;
                        	
                          }
						
					  
					}
					else {
						
						  while(true) {	
							
							  System.out.println("Username : ");
							  String username = in.next();
							  System.out.println("Password : ");
							  String password = in.next();
	                          if(username.equals(Studentlist.get(i).username) && password.equals(Studentlist.get(i).password)) {
	                        	  inital_user.displayMenu();
	                        	  while(true) {
	                        		  
	                        		  System.out.println();
	                        		  Studentlist.get(i).displayMenu();
		                              switch(in.nextInt()) {
		                            
		                              case 1 : Studentlist.get(i).viewCourses(Courselist);break;
		                              case 2 : Studentlist.get(i).viewNotFullCourses(Courselist);break;
		                              case 3 : Studentlist.get(i).self_register(Courselist);break;
		                              case 4 : Studentlist.get(i).withdraw(Courselist, Studentlist.get(i).fullName);break;
		                              case 5 : Studentlist.get(i).coursesRegistered(Courselist);break;
		                              case 0 : Studentlist.get(i).Serialize(Courselist, Studentlist);
		                              }

	                        		  
	                        	  }
	                        	  	                          }
	                          else {
	                        	
	                        	  System.out.println("Wrong username or password, try again");
	                        	  continue;
	                        	
	                          }
							
						  }
						
						
						
						
					}
						
			    }
			
			}
			System.out.println("No such student exists in the registry, enter 0 to exit or 1 to try again");
			if(in.nextInt() == 1) {
				continue;
			}
			else {
				in.close();
				System.exit(0);
			}
		  }
		}
		 
		
		
			
    }
				
}

class Course implements java.io.Serializable {
	
	private final static long serialVersionUID = 1L;
	private String Name;
	private String ID;
	int capacity;
	int currentNum;
	String Instructor;
	int secNum;
	String location;
	ArrayList<String> studentNames = new ArrayList<>();
	
	
	public Course(String Name, String ID, int capacity, int currentNum, ArrayList<String> studentNames, String Instructor, int secNum, String location) {
		
		this.Name = Name;
		this.ID = ID;
		this.capacity = capacity;
		this.currentNum = currentNum;
		this.Instructor = Instructor;
		this.secNum = secNum;
		this.location = location;
		
		
	}
	//setter list which doesn't include setters for private variables name and ID
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public void setInstructor(String instructor) {
		Instructor = instructor;
	}

	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStudentNames(ArrayList<String> studentNames) {
		this.studentNames = studentNames;
	}

	public String getID(){
		
		
		return this.ID;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public void displayInfo() {
		
		System.out.println("Name : " + this.Name + "------" + "ID : " + this.ID + "------" + "Instructor : " + this.Instructor + "------" + "Section Number : " + this.secNum + "------" + "Location : " + this.location);
		
		
	}
	public boolean isFull () {
		
		if(this.currentNum == this.capacity) {
			
			return true;
		}
		
		else {
			
			return false;
		}
	}
	
	
}

class User implements java.io.Serializable{
	
	private final static long serialVersionUID = 1L;
	String username;
	String password;
	String FirstName;
	String LastName;
	String fullName;
	
	public User() {
		
	}
	
	
	public User(String username, String password, String FirstName, String LastName) {
		
		this.username = username;
		this.password = password;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.fullName = FirstName + " " + LastName;
		
	}
	
	public void viewCourses(ArrayList<Course> CourseList) {
		
		for(int i=0; i < CourseList.size(); i++) {
			
			CourseList.get(i).displayInfo();
			System.out.println();
		}
		
	}
	
	public void displayMenu() {
		
		System.out.println("---------Welcome---------");
	}
	//inherited method for all users , so that when they logout they save their changes
	public void Serialize (ArrayList<Course> CourseList, ArrayList<Student> StudentList) {
		
		try {
			//FileOutput Stream writes data to a file
			  FileOutputStream fos = new FileOutputStream("Courses.ser");
			  FileOutputStream fos_1 = new FileOutputStream("Students.ser");
			
			//ObjectOutputStream writes objects to a stream (A sequence of data)
			  ObjectOutputStream oos = new ObjectOutputStream(fos);
			  ObjectOutputStream oos_1 = new ObjectOutputStream(fos_1);
			
			//Writes the specific object to the OOS
			  for(int j = 0; j < CourseList.size(); j++) {
			        oos.writeObject(CourseList.get(j));
			  }
			  
			  for(int i = 0; i < StudentList.size(); i++) {
				  oos_1.writeObject(StudentList.get(i));
			  }
			//Close both streams
			  oos.close();
			  fos.close();
			  oos_1.close();
			  fos_1.close();
			  System.out.println("Exiting.....Thank you for using my CRS");
		    } 
		    catch (IOException ioe) {
			ioe.printStackTrace();
		    }
		
		System.exit(0);
	}
}

interface AdminClass{
	
	public void Createcourse(ArrayList<Course> CourseList);
	public void deleteCourse(ArrayList<Course> CourseList);
	public void registerStudent(ArrayList<Student> StudentList);
	public void viewCourses(ArrayList<Course> CourseList);
	public void viewFullCourses(ArrayList<Course> CourseList);
	public void viewStudents(ArrayList<Course> CourseList);
	public void viewCoursesOfaStudent(ArrayList<Course> CourseList);
	public void displayInfo(ArrayList<Course> CourseList , ArrayList<Student> StudentList);
	public void displayInfo(ArrayList<Course> CourseList );
}
// general note : methods in the upcoming classes contain local scanner objects which have not been closed. Closing interferes with serialization for some reason. 
// Also local "indicator" variables are meant to handle exceptions smoothly, however there might still be exceptions that have not been handled.
class Admin extends User implements AdminClass , java.io.Serializable{
	
	
	private final static long serialVersionUID = 1L;
	ArrayList<Student> StudentList = new ArrayList<>();
	
	
	
	public Admin(String username , String password , String firstname, String lastname) {
		super(username , password , null, null);
	}
	
	public void displayMenu() {
		

		System.out.println(" To create a new course enter 1"); 
		System.out.println(" To delete a course enter 2");
		System.out.println(" To Edit a course enter 3");
		System.out.println(" To Display information for a course enter 4");
		System.out.println(" To Register a student enter 5");
		System.out.println(" To view details about a course such as Number of students registered along with their names and usernames and Maximum number of students allowed enter 6");
		System.out.println(" To view all FULL courses enter 7");
		System.out.println(" To write to a file list of courses that are full enter 8");
		System.out.println(" To view the names of the students registered in a specific course enter 9");
		System.out.println(" To view the list of courses a given student is registered in enter 10");
		System.out.println(" To sort courses enter 11");
		System.out.println(" To view course list enter 12");
		System.out.println(" To exit enter 0");
	}
	
	public void Createcourse(ArrayList<Course> CourseList) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter name of the course : ");
	    String Name = in.nextLine();
	    System.out.println("Enter ID : ");
	    String ID = in.next();
	    System.out.println("Enter student capacity of course : ");
	    int capacity = in.nextInt();
	    in.nextLine();
	    System.out.println("Enter Instructor name : ");
	    String Instructor = in.nextLine();
	    System.out.println("Enter section numner : ");
	    int secNum = in.nextInt();
	    System.out.println("Enter location : ");
	    String location = in.next();
	    //assume new courses are empty
		Course new_course =  new Course(Name, ID, capacity, 0, null, Instructor, secNum, location);
		
		CourseList.add(new_course);
		
		System.out.println("Course has been created");
		new_course.displayInfo();
	}
	
	
	
	public void deleteCourse(ArrayList<Course> CourseList) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter Course ID  : ");
		String ID = in.next();
		System.out.println("Enter Section Number of the course : ");
		int section = in.nextInt();
		int indicator = 0;
		
		for(int i=0; i<CourseList.size(); i++) {
			
			if(ID.equals(CourseList.get(i).getID()) && section == CourseList.get(i).secNum) {
				indicator++;
				System.out.println("Removing .... " + " " + ID + " " + CourseList.get(i).getName() );
				CourseList.remove(i);
				System.out.println("Done");
				
				
			}
			
			else if(CourseList.size() - i == 1 && indicator == 0) {
				
				System.out.println("Course to be deleted not found");
			}
		}
		
		
	}
	
	public void editCourse(ArrayList<Course> CourseList) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter ID of the course you want to edit");
		String ID = in.next();
		System.out.println("Enter Section Number of the course : ");
		int section = in.nextInt();
		for(int i = 0; i < CourseList.size(); i++) {
			if(ID.equals(CourseList.get(i).getID()) && section == CourseList.get(i).secNum) {
				System.out.println("To edit capacity of the course enter 1 ");
				System.out.println("To edit Instructor of the course enter 2 ");
				System.out.println("To edit Section Number of the course enter 3");
				System.out.println("To edit Location of the course enter 4");
				
				switch(in.nextInt()) {
				         
				case 1 : System.out.println("Enter new capacity");
				         CourseList.get(i).setCapacity(in.nextInt());break;
				case 2 : in.nextLine();
				         System.out.println("Enter new Instructor name");
				         CourseList.get(i).setInstructor(in.nextLine());break;
				case 3 : in.nextLine();
				         System.out.println("Enter new Section Number : " ) ;
				         CourseList.get(i).setSecNum(in.nextInt());in.nextLine();break;
				case 4 : in.nextLine();
				         System.out.println("Enter new location : " );
				         CourseList.get(i).setLocation(in.nextLine());break;
				}
				
			}
		}
		
	}
	
	public void displayInfo(ArrayList<Course> CourseList, ArrayList<Student> StudentList) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Course ID of course you want to view : ");
		String ID = in.next();
		System.out.println("Enter Section number of the course");
		int section = in.nextInt();
		
		for(int i = 0; i < CourseList.size(); i++){
		  if(ID.equals(CourseList.get(i).getID()) && section == CourseList.get(i).secNum) {
			  
			    System.out.println("Current Number : " +  CourseList.get(i).currentNum);
				System.out.println("Maximum number of students allowed : " + CourseList.get(i).capacity);
				System.out.println("Name and Username of Students registered in the course : " );
				if(CourseList.get(i).studentNames.size() == 0) {
					System.out.println("No students registered in the course");
		
				}
				
				
				else {
					for(int j = 0 ; j < CourseList.get(i).studentNames.size(); j++) {
						System.out.println(CourseList.get(i).studentNames.get(j));
						for(int k = 0; k < StudentList.size(); k++) {
							if(StudentList.get(k).fullName.equals(CourseList.get(i).studentNames.get(j))){
								System.out.println(StudentList.get(k).username);
							}
						}
					}
					
				}
				
				
		
			  break;
		  }
		  
		  else if(CourseList.size() - i == 1) {
			  
			  System.out.println("No such course exists");
			
		  }
			
		}
	}
	public void registerStudent(ArrayList<Student> StudentList) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter first name of the student : ");
		String firstName = in.next();
		System.out.println("Enter last name of the student : ");
		String lastName = in.next();
		
		
		Student new_Student = new Student(" ", " ", firstName,lastName);
		
		StudentList.add(new_Student);
		System.out.println(new_Student.fullName + " has been registered");
		
		
	}
	
	public void displayInfo(ArrayList<Course> CourseList) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter ID");
		String ID = in.nextLine();
		System.out.println("Enter Section Number of the course : ");
		int section = in.nextInt();
	 	int indicator = 0;
        for(int i=0; i<CourseList.size(); i++) {
			
			if(ID.equals(CourseList.get(i).getID()) && section == CourseList.get(i).secNum) {
				indicator++;
				CourseList.get(i).displayInfo();
				break;
			}
			
            else if (CourseList.size() - i == 1 && indicator == 0) {
				
				System.out.println("Invalid ID or Section Number. No such course exists");
			}
        }
	}
	
	public void viewFullCourses(ArrayList<Course> CourseList) {
		
		for(int i=0; i<CourseList.size(); i++) {
			
			if(CourseList.get(i).isFull() == true) {
				
				System.out.println(CourseList.get(i).getID() + " Section Number " + CourseList.get(i).secNum + " is full");
				System.out.println();
			}
		}
	}
	
	public void viewStudents(ArrayList<Course> CourseList) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Course ID : ");
		String ID = in.next();
		System.out.println("Enter Section Number of the course : ");
		int section = in.nextInt();
		int indicator = 0;
		
		
		for(int i=0; i<CourseList.size(); i++) {
			
			if(ID.equals(CourseList.get(i).getID()) && section == CourseList.get(i).secNum) {
			  
			  indicator++;	
			  if(CourseList.get(i).studentNames.size() == 0) {
				  System.out.println("No students registered in this course");
			  }
			  
			  else {
				  
				  for(int j=0; j<CourseList.get(i).studentNames.size(); j++) {
						
						System.out.println(CourseList.get(i).studentNames.get(j));
						continue;
					}
				  
			  }	
				
			}
			
		    else if (CourseList.size() - i == 1 && indicator == 0) {
				
				System.out.println("Invalid ID or Section Number. No such course exists");
			}
		}
		
		
	}
	
	public void viewCoursesOfaStudent(ArrayList<Course> CourseList) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter full name of the student : ");
		String name = in.nextLine();
		int indicator = 0;
		
		for(int i=0; i<CourseList.size(); i++) {
			
			for(int j=0; j<CourseList.get(i).studentNames.size(); j++) {
				
				if(name.equals(CourseList.get(i).studentNames.get(j))) {
					indicator++;
					System.out.println(CourseList.get(i).getName());
					continue;
				}
				
				
			
			}
			
			if(CourseList.size() - i == 1 && indicator == 0) {
				System.out.println("This student is either not registered in any courses or has not been registered by the admin");
			}
			
		}
		
	}
	public void writeFullCourses(ArrayList<Course> CourseList) throws FileNotFoundException {
		try {
			FileWriter writer = new FileWriter("Full_Courses.txt");
			for(int i = 0; i < CourseList.size(); i++) {
				if(CourseList.get(i).currentNum == CourseList.get(i).capacity) {
					writer.write("Name : " + CourseList.get(i).getName() + " | " + " Section Number : " +  CourseList.get(i).secNum);
				}
			}
			System.out.println("Names and Section numbers of the courses that have reached capacity have been written to file Full_Courses.csv");
			System.out.println();
			writer.close();
		}
		
		catch(IOException ioe) {
		     ioe.printStackTrace();
		     return;
		}
	}
	
	public void sortCourses(ArrayList<Course> CourseList) {
		
		for(int i = 0;i < CourseList.size(); i++) {
			
			for(int j = i+1;j<CourseList.size(); j++) {
				
				if(CourseList.get(i).currentNum < CourseList.get(j).currentNum) {
					
					Collections.swap(CourseList, i, j);
					
				}
				
				else {
					
					continue;
				}
			}
		}
		System.out.println("Courses have been sorted in ascending order according to number of students registered.");
		System.out.println();
		this.viewCourses(CourseList);
	}
	
}

interface StudentClass{
	
	public void viewNotFullCourses(ArrayList<Course> CourseList);
	public void self_register(ArrayList<Course> CourseList);
	public void withdraw(ArrayList<Course> CourseList, String full_name);
	public void coursesRegistered(ArrayList<Course> CourseList);
	public boolean isUnique(ArrayList<Student> StudentList, String username);
	
}

class Student extends User implements StudentClass , java.io.Serializable{
	
	
	private final static long serialVersionUID = 1L;
	public Student(String username, String password, String firstName, String lastName) {
		
		super(username, password, firstName, lastName);
	
	}
	
	
	public void displayMenu() {
		
		
    	System.out.println("To view all courses enter 1");
    	System.out.println("To view all courses that are not full enter 2");
    	System.out.println("To register in a course enter 3");
    	System.out.println("To withdraw from a course enter 4");
    	System.out.println("To view all courses that you are registered in enter 5");
    	System.out.println("To exit enter 0");
		
	}
	
	public void viewNotFullCourses(ArrayList<Course> CourseList) {
		
		for(int i=0; i<CourseList.size(); i++) {
			
			if(CourseList.get(i).isFull() == false) {
				
				System.out.println(CourseList.get(i).getName() + " Section " + CourseList.get(i).secNum + " is not full ");
			}
		}
		
		
	}
	
	public void self_register(ArrayList<Course> CourseList) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter the ID of the course : ");
		String ID = in.next();
		System.out.println("Enter Section Number of the course : ");
		int section = in.nextInt();
		int indicator = 0;
		for(int i=0; i<CourseList.size(); i++) {
			
			if(ID.equals(CourseList.get(i).getID()) && section == CourseList.get(i).secNum) {
				indicator++;
				if(CourseList.get(i).isFull() == false) {
					
					CourseList.get(i).studentNames.add(this.fullName);
					CourseList.get(i).currentNum ++;
					System.out.println(this.fullName + " You have been added to " + CourseList.get(i).getName());
					break;
					
				}
				
				else {
					
					System.out.println("Course is full, can not add");
					break;
				}
				
				
			}
			
			else if (CourseList.size() - i == 1 && indicator == 0) {
				System.out.println("Course not found");
			}
		}
		
		
		
	}
	
	public void withdraw(ArrayList<Course> CourseList, String full_name) {
        Scanner in = new Scanner(System.in);
		System.out.println("Enter the ID of the course : ");
		String ID = in.next();
		System.out.println("Enter Section Number of the course : ");
		int section = in.nextInt();
		int indicator =0;
		int indicator_2 =0;
		for(int i=0; i<CourseList.size(); i++) {
			
			if(ID.equals(CourseList.get(i).getID()) && section == CourseList.get(i).secNum) {
			  indicator++;
			  if(CourseList.get(i).studentNames.size() != 0) {
				  
				  for(int j =0; j<CourseList.get(i).studentNames.size(); j++) {
						
						if(CourseList.get(i).studentNames.get(j).equals(full_name)) {
							indicator_2++;
							CourseList.get(i).studentNames.remove(j);
							System.out.println("You have been removed from the course");
						}
						
						else if (CourseList.get(i).studentNames.size() - j == 1 && indicator_2 == 0) {
							
							System.out.println("You are not registered in this course");
						}
					}
				  
			  }
			  
			  else {
				  System.out.println("You are not registered in this course");
			  }
				
				
			}
			
			else if(CourseList.size() - i == 1 && indicator == 0){
				
				System.out.println("Course not found");
			}
			
			
		}
	}
	
	public void coursesRegistered(ArrayList<Course> CourseList) {
		
		int indicator = 0;
		for(int i=0; i<CourseList.size(); i++) {
			
			for(int j=0; j<CourseList.get(i).studentNames.size(); j++) {
				
				if(this.fullName.equals(CourseList.get(i).studentNames.get(j))) {
					indicator++;
					System.out.println(CourseList.get(i).getName());
					
				}
			}
			if(CourseList.size() - i == 1 && indicator == 0) {
				System.out.println("You are not registered in any courses yet");
			}
		}
	}
	
	public boolean isUnique(ArrayList<Student> StudentList, String username) {
		if(StudentList.size() == 0 || StudentList.size() == 1) {
			return true;
		}
		
		else {
			
			for(int i = 0; i< StudentList.size() - 1; i++) {
				
				if(StudentList.get(i).username.equals(username)) {
					return false;
				}
				
			}
			return true;
			
		}
	
	}
	

}