# MangaWebsite

[![Last Commit](https://img.shields.io/github/last-commit/DoriXon55/MangaWebsite)](https://github.com/DoriXon55/MangaWebsite/commits/main)

## Overview

MangaWebsite is a web application built with Spring Boot, designed for searching and managing manga online. It leverages the Jikan API to provide a platform for users to discover, track, and organize their favorite manga series.

*   **Manga Search:** Utilizes the Jikan API to search for manga titles and retrieve detailed information.
*   **Manga Tracking:** Allows users to add manga to their accounts and assign different statuses (e.g., "Reading," "Completed," "Plan to Read").
*   **User Accounts:** Users can create accounts to manage their manga lists.
*   **Embedded Database:** User accounts and manga data are stored in an embedded database for ease of setup and deployment.
*   **Responsive Design:** Ensures the website is accessible and usable on various devices.

## Technologies Used

*   **HTML:** Provides the structure and content of the website.
*   **CSS:** Styles the website and ensures a visually appealing user interface.
*   **Java:** Implements the backend logic using Spring Boot.
*   **Spring Boot:** Simplifies the development of Java-based web applications and provides features like dependency injection, auto-configuration, and embedded server support.
*   **Jikan API:** Provides access to a comprehensive database of manga information.
*   **Embedded Database:** Stores user accounts and manga data (H2).


## How to Run the Project

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/DoriXon55/MangaWebsite.git
    ```
2.  **Import into IDE:**

    *   Import the project into your preferred IDE (IntelliJ IDEA, Eclipse, etc.).
3.  **Run the application:**

    *   Run the `MangaWebsiteApplication.java` file as a Java application. Spring Boot will start the embedded server and deploy the application.
4.  **Access the website:**

    *   Open your web browser and navigate to the appropriate URL (e.g., `http://localhost:8080`).

## Embedded Database Note

This project uses an embedded database for storing user accounts and manga data. This simplifies the setup and deployment process, as no external database server is required. However, it's important to note that data stored in an embedded database is typically not persistent across application restarts. For a production environment, it's recommended to migrate to a persistent database like MySQL or maybe Google Firebase.

## Potential Improvements (for future)

*   **Migrate to a Persistent Database:** Use a database like MySQL or PostgreSQL for production deployments.
*   **Enhance User Interface:** Improve the visual design and user experience of the website.
*   **Implement Advanced Search:** Add filtering and sorting options to the manga search functionality.
*   **Implement Recommendations:** Suggest manga to users based on their reading history or preferences.

