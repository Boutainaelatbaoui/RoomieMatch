# RommieMatch 💼🏠

RommieMatch is a comprehensive web application designed to facilitate the process of finding compatible roommates based on individual preferences and questionnaires. With its intuitive interface and sophisticated matching algorithm, RommieMatch aims to streamline the roommate search process, ensuring users are connected with compatible individuals for shared living arrangements.

## Problem Statement 🤔

Finding the right roommate can be a daunting task, often involving countless hours of searching through listings and conducting interviews. Traditional methods of finding roommates often lack the efficiency and accuracy needed to ensure a harmonious living situation. Moreover, individuals may have specific preferences and requirements when it comes to choosing a roommate, making the process even more challenging.

## Solution 💡

RommieMatch addresses these challenges by providing a centralized platform where users can create detailed profiles, outlining their preferences and requirements for a roommate. The application utilizes a sophisticated matching algorithm that analyzes user responses to a series of questions, generating personalized matches based on compatibility. By considering factors such as lifestyle preferences, budget constraints, and location preferences, RommieMatch ensures that users are connected with compatible roommates who meet their specific criteria.

## Features 🚀

### Technologies Used:
- **Backend Development**: Spring Boot
- **Frontend Development**: Angular
- **Authentication and Authorization**: Spring Security and JWT (JSON Web Tokens)
- **Database Management**: JPA (Java Persistence API) with Hibernate
- **Containerization**: Docker
- **Unit Testing**: JUnit, Mockito
- **Project Management**: Maven
- **Database Schema Management**: Liquibase
- **Development Environment**: IntelliJ IDEA, Visual Studio Code
- **Version Control**: Git
- **API Testing**: Postman
- **Project Planning and Tracking**: JIRA

### Detailed Features:
- **User Authentication and Authorization**: Users need to authenticate themselves to access the system. JWT tokens are used for secure authentication.
- **Questionnaire Matching**: Users fill out detailed questionnaires about their preferences. The system then matches users based on their responses, ranging from a high percentage match (100%) to a low percentage match (10%).
- **Consistent Questionnaire Format**: Ensures consistency in questionnaire format across all questions for accurate matching.
- **Filtering Options**: Users can filter matches based on gender, apartment budget, current city, and target city. A minimum of 3 compatible preferences is required for matching.
- **Age Filtering**: Users can filter matches based on age through the questionnaire.
- **Match Requests**: Users can send match requests to other users with the same gender. Upon acceptance, users can view the email and phone number of their potential roommate for contact purposes.
- **Profile Management**: Users can modify their personal information and email address.
- **Search Functionality**: Users can search for other users by name.
- **Admin and Manager Functionalities**: Admins and managers have full access to create, delete, view, and edit user profiles.
- **Notification System**: Users receive notifications for sent or accepted match requests.

## Usage 🛠️

1. **Clone the repository:**

    ```
    git clone <repository_url>
    ```

2. **Set up the development environment:**

    - Install IntelliJ IDEA and Visual Studio Code.
    - Ensure Docker is installed and running.

3. **Build and run the application:**

    - Navigate to the project directory.
    - Run the following command to build and run the application:

    ```bash
    mvn clean install
    docker-compose up --build
    ```

4. **Access the application:**

    - Once the application is up and running, access it via the specified URL.
