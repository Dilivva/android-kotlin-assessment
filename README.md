# Kotlin Mobile Developer Assessment

![Dilivva Logo](./assets/dilivva-logo.png)

Welcome to the **Kotlin Mobile Developer Assessment** for Send24 (Dilivva Int'l Tech Inc). This test is designed to evaluate mid-level Kotlin developers' skills in building modern Mobile applications, implementing best practices, and using Git workflows effectively.

---

## 🎯 **Objective**  

The goal of this assessment is to:  
- Assess your knowledge of **Kotlin** and **modern Android development**.  
- Test your proficiency in using **Jetpack Components**, **state management**, and **API integration**.  
- Evaluate your ability to implement **UI/UX best practices** and ensure responsiveness.  
- Examine your Git collaboration skills (branching, commits, and pull requests).  

---

![Kotlin Logo](https://upload.wikimedia.org/wikipedia/commons/1/11/Kotlin_logo_2021.svg)  

---

## 📝 **Task Overview**  

You are required to create a **Delivery Weather Insights App** for Send24. The application will fetch weather data for given pickup and drop-off locations and provide recommendations for delivery feasibility. This task allows you to optionally use **Kotlin Multiplatform** for parts of the implementation.

---

## 🚀 **Requirements**  

### 1. **Core Features**  
1. **Input Fields**: Allow users to input pickup and drop-off locations.  
2. **Weather Data**: Fetch and display the following details for both locations:  
   - Temperature.  
   - Weather conditions (e.g., rain, clear skies).  
   - Wind speed (optional).  
3. **Delivery Recommendation**: Provide a recommendation based on weather conditions:  
   - "Safe for delivery" (e.g., clear skies, light rain).  
   - "Delay advised" (e.g., heavy rain, thunderstorms).  
4. **History**: Save the last 5 routes searched locally (Room or SharedPreferences).  

### 2. **Optional Kotlin Multiplatform (KMP)**  
- Use a **shared Kotlin Multiplatform module** to handle weather data processing and recommendations.  
- Test the shared logic with unit tests.  

### 3. **User Interface**  
- Use **Jetpack Compose** or **XML layouts** to design a clean, responsive UI.  
- Display weather data and recommendations clearly.  

---

##  🛠️  **Instructions**

1. **Access the Repository:**
   - You have been granted access to this repository for the duration of the assessment.

2. **Clone the Repository:**
   ```bash
   git clone <repository-url>
   cd kotlin-mobile-assessment
   ```

3. **Create Your Branch:**
   - Create and switch to your working branch:
   - Branch name: `firstname-lastname-assessment`

4. **Implement the Features:**
   - Follow the requirements outlined above.
   - Commit your changes with clear and concise messages:
     ```bash
     git commit -m "Added ... functionality to the ..."
     ```

6. **Push To Your Branch:**
   ```bash
   git push origin firstname-lastname-assessment
   ```

7. **Optional - Create a Pull Request (PR):**
   - Open a pull request from your branch to the `firstname-lastname-submit` branch.
   - Add a description summarizing your work, or stating extra information for the review committee 

---

## 📊 **Evaluation Criteria**

Your submission will be evaluated on the following:
- **Code Quality:** Readability, organization, and adherence to best practices.
- **Functionality:** Completeness of features and bug-free implementation.
- **UI/UX:** Visual appeal and user experience.
- **Git Usage:** Proper use of branches, commits, and pull requests.
- **Error Handling:** Graceful handling of API errors and edge cases.
- **Timeliness:** Timeliness is key, even if you are not able to finish it up.

---

## 📝 **Additional Notes**

- You can use any external libraries that you find helpful, but be free to explain your choices.
- Ensure your application runs without errors before submitting, its ok if it doesnt though 🤪🤪.
- If you have any questions, feel free to reach out to the Head of the Technical Assessment committee `enochoyerinde(at)send24(dot)co` .

---

## ⏳ **Submission Deadline**

All branches must be pushed 2Hhrs 30Min upon accepting the assessment email.

---

We look forward to reviewing your work! Good luck!
