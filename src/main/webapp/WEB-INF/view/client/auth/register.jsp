<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Create Account</title>
                <link href="/client/css/register.css" rel="stylesheet">
            </head>

            <body>
                <div class="container">
                    <h1 class="title">Create Account</h1>

                    <form:form id="registerForm" method="post" enctype="multipart/form-data" action="/register"
                        modelAttribute="registerForm">
                        <div class="form-row">
                            <div class="form-group">
                                <form:input path="firstName" id="firstName" cssClass="form-control"
                                    placeholder="First name" />
                                <div class="error-message" id="firstNameError"></div>
                            </div>
                            <div class="form-group">
                                <form:input path="lastName" id="lastName" cssClass="form-control"
                                    placeholder="Last name" />
                                <div class="error-message" id="lastNameError"></div>
                            </div>
                        </div>

                        <div class="form-group full-width">
                            <form:input path="email" id="email" cssClass="form-control" placeholder="Email address" />
                            <div class="error-message" id="emailError"></div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <form:password path="password" cssClass="form-control" placeholder="Password" />
                                <div class="error-message"></div>
                            </div>
                            <div class="form-group">
                                <form:password path="confirmPassword" cssClass="form-control"
                                    placeholder="Confirm password" />
                                <div class="error-message"></div>
                            </div>
                        </div>

                        <button type="submit" class="btn">Create Account</button>
                    </form:form>

                    <div class="login-link">
                        Have an account? <a href="login.html">Go to login</a>
                    </div>
                </div>

                <!-- <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        const form = document.getElementById('registerForm');
                        const firstName = document.getElementById('firstName');
                        const lastName = document.getElementById('lastName');
                        const email = document.getElementById('email');
                        const password = document.getElementById('password');
                        const confirmPassword = document.getElementById('confirmPassword');

                        // Clear error messages
                        function clearErrors() {
                            document.querySelectorAll('.error-message').forEach(error => {
                                error.style.display = 'none';
                                error.textContent = '';
                            });
                        }

                        // Show error message
                        function showError(fieldId, message) {
                            const errorElement = document.getElementById(fieldId + 'Error');
                            errorElement.textContent = message;
                            errorElement.style.display = 'block';
                        }

                        // Validate email format
                        function isValidEmail(email) {
                            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                            return emailRegex.test(email);
                        }

                        // Validate password strength
                        function isValidPassword(password) {
                            return password.length >= 6;
                        }

                        // Form submission
                        form.addEventListener('submit', function (e) {
                            e.preventDefault();
                            clearErrors();

                            let isValid = true;

                            // Validate first name
                            if (!firstName.value.trim()) {
                                showError('firstName', 'First name is required');
                                isValid = false;
                            }

                            // Validate last name
                            if (!lastName.value.trim()) {
                                showError('lastName', 'Last name is required');
                                isValid = false;
                            }

                            // Validate email
                            if (!email.value.trim()) {
                                showError('email', 'Email is required');
                                isValid = false;
                            } else if (!isValidEmail(email.value)) {
                                showError('email', 'Please enter a valid email address');
                                isValid = false;
                            }

                            // Validate password
                            if (!password.value) {
                                showError('password', 'Password is required');
                                isValid = false;
                            } else if (!isValidPassword(password.value)) {
                                showError('password', 'Password must be at least 6 characters long');
                                isValid = false;
                            }

                            // Validate confirm password
                            if (!confirmPassword.value) {
                                showError('confirmPassword', 'Please confirm your password');
                                isValid = false;
                            } else if (password.value !== confirmPassword.value) {
                                showError('confirmPassword', 'Passwords do not match');
                                isValid = false;
                            }

                            if (isValid) {
                                // Simulate account creation
                                const userData = {
                                    firstName: firstName.value.trim(),
                                    lastName: lastName.value.trim(),
                                    email: email.value.trim(),
                                    password: password.value
                                };

                                console.log('Creating account with data:', userData);
                                alert('Account created successfully! Redirecting to login...');

                                // Redirect to login page
                                window.location.href = 'login.html';
                            }
                        });

                        // Real-time validation
                        confirmPassword.addEventListener('input', function () {
                            if (password.value && confirmPassword.value && password.value !== confirmPassword.value) {
                                showError('confirmPassword', 'Passwords do not match');
                            } else {
                                document.getElementById('confirmPasswordError').style.display = 'none';
                            }
                        });
                    });
                </script> -->
            </body>

            </html>