<%--
    Document   : register page
    Author     : Bob Paul
--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>
    <form action="RegisterServlet" method="POST">
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <label for="userType">User Type:</label><br>
        <select id="userType" name="userType">
            <option value="AcademicProfessional">Academic Professional</option>
            <option value="AcademicInstitution">Academic Institution</option>
        </select><br><br>

        <div id="additionalFields">
            <label for="firstName">First Name:</label><br>
            <input type="text" id="firstName" name="firstName"><br><br>

            <label for="lastName">Last Name:</label><br>
            <input type="text" id="lastName" name="lastName"><br><br>
        </div>

        <input type="submit" value="Register">
    </form>
</body>
</html>
