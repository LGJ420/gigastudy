function showAlert(message) {
    const alertModal = document.getElementById('alertModal');
    const alertContent = document.getElementById('alertContent');
    const alertMessage = document.getElementById('alertMessage');

    // 메시지 설정
    alertMessage.innerHTML = message;

    // 모달 오버레이 보이기 (display: flex 적용)
    alertModal.classList.remove('hidden');

    // 페이드인 및 아래에서 위로 떠오르기 효과 시작
    setTimeout(() => {
        alertContent.classList.remove('opacity-0', 'translate-y-4');
        alertContent.classList.add('opacity-100', 'translate-y-0');
    }, 10); // 약간의 지연을 주어 클래스 변경이 적용되도록 함

    // 알림창이 완전히 보이는 시간
    const visibleDuration = 500;

    // visibleDuration 후에 페이드아웃 및 위로 떠오르기 효과 시작
    setTimeout(() => {
        // 페이드아웃 및 위로 떠오르기 시작
        alertContent.classList.remove('opacity-100', 'translate-y-0');
        alertContent.classList.add('opacity-0', '-translate-y-4');

        // 애니메이션이 끝난 후 모달 오버레이 숨기기 (display: none으로 변경)
        setTimeout(() => {
            alertModal.classList.add('hidden');

            // 클래스 초기화 (다음 사용을 위해)
            alertContent.classList.remove('-translate-y-4');
            alertContent.classList.add('translate-y-4');
        }, 300); // 애니메이션 지속 시간과 동일하게 설정
    }, 300 + visibleDuration); // 페이드인 시간 + 알림 표시 시간
}
