function showConfirm(message, onConfirm, onCancel) {
    const confirmModal = document.getElementById('confirmModal');
    const confirmContent = document.getElementById('confirmContent');
    const confirmMessage = document.getElementById('confirmMessage');
    const confirmYes = document.getElementById('confirmYes');
    const confirmNo = document.getElementById('confirmNo');

    // 메시지 설정
    confirmMessage.textContent = message;

    // 모달 오버레이 보이기 (display: flex 적용)
    confirmModal.classList.remove('hidden');
    confirmModal.classList.add('flex');

    // 페이드인 및 아래에서 위로 떠오르기 효과 시작
    setTimeout(() => {
        confirmContent.classList.remove('opacity-0', 'translate-y-4');
        confirmContent.classList.add('opacity-100', 'translate-y-0');
    }, 10); // 약간의 지연을 주어 클래스 변경이 적용되도록 함

    // 확인 버튼 클릭 시
    confirmYes.onclick = function () {
        // 페이드아웃 및 위로 사라지기 효과
        confirmContent.classList.remove('opacity-100', 'translate-y-0');
        confirmContent.classList.add('opacity-0', '-translate-y-4');

        // 애니메이션이 끝난 후 모달창 숨기기
        setTimeout(() => {
            confirmModal.classList.add('hidden');
            confirmModal.classList.remove('flex');

            // 클래스 초기화 (다음 사용을 위해)
            confirmContent.classList.remove('-translate-y-4');
            confirmContent.classList.add('opacity-0', 'translate-y-4'); // 초기 상태로 되돌리기

            if (onConfirm) onConfirm(); // 확인 콜백 호출
        }, 300); // 애니메이션 지속 시간과 동일하게 설정
    };

    // 취소 버튼 클릭 시
    confirmNo.onclick = function () {
        // 페이드아웃 및 위로 사라지기 효과
        confirmContent.classList.remove('opacity-100', 'translate-y-0');
        confirmContent.classList.add('opacity-0', '-translate-y-4');

        // 애니메이션이 끝난 후 모달창 숨기기
        setTimeout(() => {
            confirmModal.classList.add('hidden');
            confirmModal.classList.remove('flex');

            // 클래스 초기화 (다음 사용을 위해)
            confirmContent.classList.remove('-translate-y-4');
            confirmContent.classList.add('opacity-0', 'translate-y-4'); // 초기 상태로 되돌리기

            if (onCancel) onCancel(); // 취소 콜백 호출
        }, 300); // 애니메이션 지속 시간과 동일하게 설정
    };
}
