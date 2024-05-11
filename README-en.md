[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/masrizalw/perpustakaan/blob/master/README-en.md)
[![id](https://img.shields.io/badge/lang-id-green.svg)](https://github.com/masrizalw/perpustakaan/blob/master/README.md )

# Simple Perpustakaan (Library)

This is simple perpustakaan (library) apps. Allows one user to borrow a maximum of one book. Books have stock limits. There are many roles that can be applied, but in general only two roles can be maximized. Role as administrator or role as general user.

## Appendix

This application only provides the backend. for the frontend it will be uploaded separately under the name 'Perpustakaan FE'.

## Authors

- [@masrizalw](https://www.github.com/masrizalw)

## Requirements

- JDK 21
- Maven

## Installation

Clone the project

```bash
git clone https://github.com/masrizalw/perpustakaan.git
```

Go to the project directory

```bash
cd perpustakaan
```

Compile with IDE like eclipse, intellij, etc. or via the following manual steps:

- Download and install maven
   - [install maven](https://www.baeldung.com/install-maven-on-windows-linux-mac)
   - [download link](https://maven.apache.org/download.cgi)
    
- In the root directory **/perpustakaan**, run the following command
```bash
mvn compile
mvn package
```

- The app-0.0.1-SNAPSHOT.jar file will be generated in the /perpustakaan/target directory
```bash
cd target
```

- Run the application
```bash
java -jar app-0.0.1-SNAPSHOT.jar
```

## Usage/Examples

1. Use postman or access to [localhost](http://localhost:8080/swagger)
2. To open the h2 database, access **/h2-console**, username *sa* password *root*
3. For the frontend, you can download it at the link https://github.com/masrizalw/perpustakaan-fe
4. Default username: sa Password: asdf

## TechStack

**Language:** Java
**Framework:** Springboot
**Database:** H2
