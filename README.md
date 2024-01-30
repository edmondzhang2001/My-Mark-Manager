---

# MyMarkManager

## Overview

MyMarkManager is an Android application developed in Kotlin, designed to help students manage their course marks and assessments efficiently. Utilizing modern Android development practices, the app features a user-friendly interface to add, view, and organize course marks, utilizing fragments for a dynamic user experience.

## Getting Started

### Prerequisites

- Android Studio
- Gradle
- Kotlin plugin
- Android SDK

### Setting Up the Project

1. **Clone the Repository:** Start by cloning this repository to your local machine using Android Studio's `VCS > Checkout from Version Control > Git`, or by using the command line:

   ```
   git clone https://github.com/yourusername/mymarkmanager.git
   ```

2. **Open the Project:** Open Android Studio, select `Open an existing Android Studio project`, and navigate to the cloned repository.

3. **Sync Gradle:** Ensure all dependencies are correctly synced by running a Gradle sync in Android Studio.

4. **Run the Application:** Select a suitable emulator or connect an Android device to run the project on.

## Features

- **Course Mark Management:** Add, edit, and delete course marks with ease.
- **Sorting and Filtering:** Organize your marks by course code, term, or mark, and filter them based on course categories.
- **Mark Analysis:** View your performance across different courses and terms.

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture to ensure a clean separation of concerns and enhance maintainability. Key components include:

- **Fragments:** Utilizes multiple fragments (`Fragment1`, `Fragment2`, `Fragment3`) to manage different sections of the app, such as adding a new mark, editing an existing mark, and viewing the mark list.
- **ViewModel:** `MainActivityViewModel` acts as a bridge between the UI components and the data model, handling UI-related data management and logic.
- **Data Model:** Represents course marks and related information, encapsulated in the `Entry` class.
- **Navigation:** Uses the Navigation component to manage UI navigation within the app.

## Contributing

Contributions to MyMarkManager are welcome. Before contributing, please review the [CONTRIBUTING.md](CONTRIBUTING.md) file for guidelines on how to submit issues, feature requests, and code.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
