This project is a collaboration between **Abdulrahman Muhsen, Reem Muhammad and Shady Muhammad Abdulnaby**, implemented as a final assessment for __JUPAI course, AI-pro track, ITI__


# **Project description**
Performing data analysis on [Wuzzuf Jobs data](https://www.kaggle.com/omarhanyy/wuzzuf-jobs "Wuzzuf Jobs data") and exposing the results through REST API
to be accessible as a webservice.


# **Tools used:** #
- Java 8
- HTML and Javascript
- Intellij
- maven
- Apache Spark
- Spring boot
- Highcharts
- Bootstrap


# **Get Started** #
### Firstly, Ensure proper configurations: ###
**1-Ensure you are using java 8 in all of the following places:**  
File -> Project Structure -> Project Settings  
File -> Project Structure -> Module Settings -> Tab: Sources: Language Level  
File -> Project Structure -> Module Settings -> Tab: Dependencies: Module SDK  
File -> Settings -> Compiler -> Java Compiler -> Target bytecode version  

**2- Ensure maven pom.xml runs without errors**  
**possible error:**   
Plugin 'org.springframework.boot:spring-boot-maven-plugin:' not found  
**fix:**   
navigate to "Build, Execution, Deployment > Build Tools > Maven", check the "Use plugin registry", and click "OK".  
Then, "File > Invalidate Caches / Restart" to reload Intelli J

### Secondly, run the project ###
**1-** Run the java project  
**2-** access the homepage through [localhost:8080](localhost:8080)  
**3-** Press a button to view related results  

### You can view: ###
- Part of the dataset.
- Structure of the dataset.
- Summary for the dataset.
- Companies sorted based on the number of jobs posted, 
and a pie chart showing the 10 most demanding companies.
- Jobs sorted by popularity, and a bar chart for the 10 most popular jobs.
- Locations sorted by abundance of jobs, and a bar chart for the top 10 among them
- Skills sorted by their frequency


