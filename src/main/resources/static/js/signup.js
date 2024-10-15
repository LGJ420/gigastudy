function signup() {

    let userId = document.getElementById("userId").value;
    let userPw = document.getElementById("userPw").value;
    let nickname = document.getElementById("nickname").value;

    let data = {
        userId: userId,
        userPw: userPw,
        nickname: nickname,
        role: 'USER'
    };

    alert("현재는 회원가입을 할 수 없습니다. 운영자에게 문의 바랍니다.");

    // try {
    //     fetch('/api/user', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json'
    //         },
    //         body: JSON.stringify(data)
    //     });

    //     window.location.href = '/complete';
    // }
    // catch {
    //     console.error('회원가입에 실패하였습니다.', error);
    //     document.getElementById("errorMessage").style.display = 'block';
    // }
}