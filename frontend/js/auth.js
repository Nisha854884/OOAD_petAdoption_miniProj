// Authentication Functions

// Validation Rules (matching backend DTOs)
const validationRules = {
    username: {
        minLength: 3,
        maxLength: 50,
        message: 'Username must be between 3 and 50 characters'
    },
    password: {
        minLength: 6,
        message: 'Password must be at least 6 characters'
    },
    email: {
        pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
        message: 'Email must be valid'
    },
    phone: {
        pattern: /^[0-9]{10}$/,
        message: 'Phone must be a 10-digit number'
    },
    name: {
        minLength: 2,
        maxLength: 100,
        message: 'Name must be between 2 and 100 characters'
    },
    address: {
        minLength: 5,
        maxLength: 255,
        message: 'Address must be between 5 and 255 characters'
    }
};

// Validate field
function validateField(fieldName, value) {
    const rules = validationRules[fieldName];
    if (!rules) return true;
    
    if (rules.minLength && value.length < rules.minLength) {
        return { valid: false, message: rules.message };
    }
    
    if (rules.maxLength && value.length > rules.maxLength) {
        return { valid: false, message: rules.message };
    }
    
    if (rules.pattern && !rules.pattern.test(value)) {
        return { valid: false, message: rules.message };
    }
    
    return { valid: true };
}

// Handle login
async function handleLogin(event) {
    event.preventDefault();
    
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    
    // Validation
    if (!username) {
        showError('Username is required');
        return;
    }
    
    if (!password) {
        showError('Password is required');
        return;
    }
    
    const usernameValidation = validateField('username', username);
    if (!usernameValidation.valid) {
        showError(usernameValidation.message);
        return;
    }
    
    try {
        const response = await login(username, password);
        
        if (response.includes('Invalid credentials')) {
            showError('Invalid username or password');
            return;
        }
        
        // Extract role from response
        let role = 'Adopter'; // default
        if (response.includes('Admin')) role = 'Admin';
        if (response.includes('Staff')) role = 'Staff';
        
        // Store user info
        const userData = {
            username: username,
            role: role,
            loginTime: new Date().toISOString()
        };
        localStorage.setItem('currentUser', JSON.stringify(userData));
        
        showSuccess('Login successful!');
        
        // Redirect based on role
        setTimeout(() => {
            if (role === 'Admin') {
                window.location.href = 'admin-dashboard.html';
            } else if (role === 'Staff') {
                window.location.href = 'staff-dashboard.html';
            } else {
                window.location.href = 'adopter-dashboard.html';
            }
        }, 1000);
        
    } catch (error) {
        showError('Login failed: ' + error.message);
    }
}

// Handle signup
async function handleSignup(event) {
    event.preventDefault();
    
    const username = document.getElementById('signup-username').value.trim();
    const password = document.getElementById('signup-password').value;
    const confirmPassword = document.getElementById('signup-confirm-password').value;
    
    // Validation
    if (!username) {
        showError('Username is required');
        return;
    }
    
    const usernameValidation = validateField('username', username);
    if (!usernameValidation.valid) {
        showError(usernameValidation.message);
        return;
    }
    
    if (!password) {
        showError('Password is required');
        return;
    }
    
    const passwordValidation = validateField('password', password);
    if (!passwordValidation.valid) {
        showError(passwordValidation.message);
        return;
    }
    
    if (password !== confirmPassword) {
        showError('Passwords do not match');
        return;
    }
    
    try {
        const response = await signup(username, password);
        
        if (response && response.includes('successful')) {
            showSuccess('Signup successful! Please login.');
            
            // Switch to login tab
            setTimeout(() => {
                document.getElementById('login-tab').click();
                document.getElementById('signup-username').value = '';
                document.getElementById('signup-password').value = '';
                document.getElementById('signup-confirm-password').value = '';
            }, 1500);
        } else {
            showError('Signup failed. Username may already exist.');
        }
    } catch (error) {
        showError('Signup error: ' + error.message);
    }
}

// Toggle between login and signup
function toggleAuthForm(tab) {
    const loginForm = document.getElementById('loginForm');
    const signupForm = document.getElementById('signupForm');
    const loginTab = document.querySelectorAll('.tab-btn')[0];
    const signupTab = document.querySelectorAll('.tab-btn')[1];
    
    if (tab === 'login') {
        loginForm.style.display = 'block';
        signupForm.style.display = 'none';
        loginTab.classList.add('active');
        signupTab.classList.remove('active');
    } else {
        loginForm.style.display = 'none';
        signupForm.style.display = 'block';
        loginTab.classList.remove('active');
        signupTab.classList.add('active');
    }
}

// Check if user is logged in on page load
function checkAuthStatus() {
    if (!isLoggedIn()) {
        window.location.href = 'login.html';
    }
}

// Redirect if already logged in
function preventLoggedInAccess() {
    if (isLoggedIn()) {
        const user = getCurrentUser();
        if (user.role === 'Admin') {
            window.location.href = 'admin-dashboard.html';
        } else if (user.role === 'Staff') {
            window.location.href = 'staff-dashboard.html';
        } else {
            window.location.href = 'adopter-dashboard.html';
        }
    }
}
