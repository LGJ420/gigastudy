document.addEventListener("DOMContentLoaded", function () {

    let accessToken = localStorage.getItem('accessToken');

    if (accessToken) {
        // JWT가 존재하면 로그아웃 버튼을 표시
        document.getElementById("authButtons").innerHTML =
            '<div class="hover:opacity-30 cursor-pointer" onclick="logout()" class="hover:opacity-30">로그아웃</div>';
    } else {
        // JWT가 없으면 로그인 버튼을 표시
        document.getElementById("authButtons").innerHTML =
            '<a href="/login" class="hover:opacity-30">로그인</a>';
    }
    
});

function logout() {

    // 로그아웃 시 토큰 삭제
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    window.location.href = '/';  // 로그아웃 후 메인 페이지로 리다이렉트
}