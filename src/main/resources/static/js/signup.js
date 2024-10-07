function signup() {

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let nickname = document.getElementById("nickname").value;

    let data = {
        username: username,
        password: password,
        nickname: nickname,
        role: 'USER'
    };

    try {
        fetch('/api/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        window.location.href = '/complete';
    }
    catch {
        console.error('Error:', error);
        document.getElementById("errorMessage").style.display = 'block';
    }
}