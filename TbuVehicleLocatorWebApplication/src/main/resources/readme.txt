							
									Deployment Process Of vehiclelocator.war
									-----------------------------------------
						Pre-Requisite :
						Appache Tomcat Server 8.x version
						MySql Server 5.7
						Maven Building Tool
						MYSQL WOrkbench 6.3 [Optional]
												
						A]Grant Remote Access of MySQL database
						------------------------------------------------------
						Use Your Default username and password to run below Command :						
						CREATE USER 'username' IDENTIFIED BY 'password';
						GRANT ALL PRIVILEGES ON *.* TO 'username'@'localhost' WITH GRANT OPTION;
						GRANT ALL PRIVILEGES ON *.* TO 'username'@'%' WITH GRANT OPTION;
						FLUSH PRIVILEGES;
						============================================================================
						B]Deployment Steps :
						1.Copy the 	vehiclelocator.war file in webapp folder of Tomcat
						2.Start tomcat server by clicking startup bat file
						3.Request the url : http://<SERVER-IP>:<PORT>/vehiclelocator/
							SERVER-IP : Is the IP where the Tomcat server is hosted
							PORT	  :	Is Port where request is listen by server eg.8080
						4.After hitting url,database config page will be appeared,Fill all required information
						=========================================================================================
						C]Re-Deployment Process Of War[If Modification required in existing code but having existing  production database connection]
						-----------------------------------------------------------------------------------------------------------------
						Create war with existing production database details as Follows :
						#JDBC Driver for Database Connection
						jdbc.driver=com.mysql.jdbc.Driver
						#Connecting URL
						jdbc.url=jdbc:mysql://10.20.25.62:3306/tbu
						# DB Username
						jdbc.username=tbu
						# DB Password
						jdbc.password=tbu@123
						#If DB is already configured
						jdbc.dbconfig=Y
						
						NOTE:
						jdbc.dbconfig=Y/N
						if want to continue using the already configured production database, Keep Y
						If not,then create new database by keeping N.								
						and Follow the steps B
						==============================================================================================
									
						Request the url : http://<SERVER-IP>:<PORT>/vehiclelocator/
						After hitting Login page will be appeared
						Default login credentials : 
						username : admin
						password : admin
						========================================================================================
						D] Production Data Base Details :
						My SQL USER NAME=tbu
						My SQL PASSWORD=tbu@123
						
						Connect Mysql On Production :
						
						mysql -u <username> -p 
						eg : mysql -u tbu -p 
						Then it will ask for the password 
						
						Enter the password : ********
						
						Then it will connect to MYSQL Database :
						
						Connect your Created Database :
						use <database name>;
						eg.use tbu;
						>> database changed
						Now just fired the sql command to check all details.
						
						===================================================================================
						E] MYSQL Workbench 6.3
						Download and set up Mysql workbench 6.3 to connect with MYSQL SERVER 
						its acts as client,we can perform all database operation through Workbench.
						
						
						