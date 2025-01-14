const mobileMenu = document.getElementById('mobileMenu');
const menuButton = document.getElementById('menuButton');
const closeButton = document.getElementById('closeButton');
const mobileMenuSupport = document.getElementById('mobileMenuSupport');

function openMenu() {
    mobileMenu.classList.remove('translate-x-full');
    mobileMenuSupport.classList.remove('hidden');
}

function closeMenu() {
    mobileMenu.classList.add('translate-x-full');
    mobileMenuSupport.classList.add('hidden');
}

menuButton.addEventListener('click', (event) => {
    openMenu();
});

closeButton.addEventListener('click', (event) => {
    closeMenu();
});

mobileMenuSupport.addEventListener('click', (event) => {
    closeMenu();
});


document.addEventListener("DOMContentLoaded", function () {
    let accessToken = localStorage.getItem('accessToken');
    let authButtons = document.querySelectorAll(".authButtons");

    authButtons.forEach(function (element) {
        if (accessToken) {
            // JWT가 존재하면 로그아웃 버튼을 표시
            element.innerHTML =
                '<div class="hover:opacity-30 cursor-pointer" onclick="logout()">로그아웃</div>';
        } else {
            // JWT가 없으면 로그인 버튼을 표시
            element.innerHTML =
                '<a href="/login" class="hover:opacity-30">로그인</a>';
        }
    });
});

function logout() {

    // 로그아웃 시 토큰 삭제
    localStorage.removeItem('accessToken');

    // 로그아웃 후 메인 페이지로 리다이렉트
    window.location.href = '/';
}