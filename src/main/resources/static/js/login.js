document.addEventListener('DOMContentLoaded', function () {
    // JWT 토큰이 localStorage에 있는지 확인
    const token = localStorage.getItem('accessToken');

    // 토큰이 있으면 로그인 페이지 접근을 차단하고 에러 페이지로 리다이렉트
    if (token) {
        window.location.href = '/';
    }
});

function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    // /login의 POST맵핑은 시큐리티에서 이미 준비되있다.
    fetch('/login', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Invalid credentials');
        }
        return response.json();
    })
    .then(data => {

        // 응답받은 JWT 토큰을 로컬 스토리지에 저장
        localStorage.setItem('accessToken', data.accessToken);

        // 리프레시 토큰은 클라이언트 측에서도 처리해야 하므로 이번 프로젝트는 생략
        // localStorage.setItem('refreshToken', data.refreshToken);

        // 홈으로 리다이렉트
        window.location.href = '/';
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById("errorMessage").style.display = 'block';
    });
}