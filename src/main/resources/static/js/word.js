let getFlag, getType;
let currentIndex = 0;
let words = [];
let turning = false;
let isAnimating = false;

// 사이트 초기화
async function init(flag, type) {


    try {

        getType = type;

        if (flag === null) {
            getFlag = false;
        } else {
            getFlag = flag
        }

        updateLoadingBar(20);




        // currentIndex 불러오기
        const indexResponse = await fetch(`/api/userindex?flag=${getFlag}&type=${type}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        currentIndex = await indexResponse.json();
        updateLoadingBar(70); // 첫번째 요청 완료 후 50%로 설정





        // 단어 리스트 불러오기
        let wordsUrl = `/api/userword?type=${type}`;

        if (flag !== null) {
            wordsUrl += `&flag=${flag}`;
        }

        const response = await fetch(wordsUrl);
        words = await response.json();
        updateLoadingBar(90); // 두번째 요청 완료 후 80%로 설정






        displayWord();
        updateLoadingBar(100); // 완료 후 100%로 설정

        // 1초 동안 로딩바가 채워지는 애니메이션 시간과 맞춤
        setTimeout(() => {
            fadeOutLoadingScreen();
        }, 1000);

    } catch (error) {
        console.error('데이터를 불러오는데 실패하였습니다.', error);
    }




    // 단축키 설정
    document.addEventListener('keydown', function (event) {

        // 애니메이션 중이면 단축키 동작 안함
        if (isAnimating) return;

        if (event.key === 'Enter') {
            handleClickNext();
        } else if (event.key === 'p') {
            handleClickSave();
        } else if (event.key === 'Backspace') {
            handleClickPre();
        }
    });
}





// 스크린 페이드아웃
function fadeOutLoadingScreen() {
    const loadingBarContainer = document.getElementById("loadingBarContainer");
    loadingBarContainer.style.opacity = '0'; // 투명하게 설정

    // 페이드아웃 애니메이션이 끝난 후 로딩바를 제거
    setTimeout(() => {
        loadingBarContainer.style.display = 'none'; // 완전히 제거
    }, 500); // 페이드아웃 시간과 맞춤 (0.5초 후에 제거)
}




// 로딩바 업데이트
function updateLoadingBar(percent) {
    const loadingBar = document.getElementById("loadingBar");
    loadingBar.style.width = percent + "%";
}




// 인덱스 저장
async function saveCurrentIndex() {
    try {
        const response = await fetch('/api/userindex', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                flag: getFlag,
                type: getType,
                saveIndex: currentIndex
            })
        });
        console.log('인덱스가 저장되었습니다.');
    } catch (error) {
        console.error('인덱스 저장에 실패하였습니다.', error);
    }
}




// 단어카드 보여주기
function displayWord() {

    const wordDiv = document.getElementById("wordDisplay");
    const meaningDiv = document.getElementById("wordMeaning");

    if (currentIndex < words.length && currentIndex >= 0) {
        const word = words[currentIndex].wordDTO.word;
        const meaning = words[currentIndex].wordDTO.meaning;

        // 글자 길이에 따라 폰트 크기를 조정
        if (word.length > 10) {
            wordDiv.style.fontSize = '2rem';
        } else if (word.length > 6) {
            wordDiv.style.fontSize = '3rem';
        } else if (word.length > 2) {
            wordDiv.style.fontSize = '5rem';
        } else {
            wordDiv.style.fontSize = '10rem';
        }

        if (meaning.length > 10) {
            meaningDiv.style.fontSize = '2rem';
        } else if (meaning.length > 6) {
            meaningDiv.style.fontSize = '3rem';
        } else {
            meaningDiv.style.fontSize = '5rem';
        }

        wordDiv.textContent = word;
        meaningDiv.textContent = meaning;

    } else {
        // 단어가 없을 때 폰트 크기를 따로 설정
        wordDiv.style.fontSize = '2rem';
        meaningDiv.style.fontSize = '2rem';
        
        wordDiv.textContent = "더 이상 단어가 없습니다.";
        meaningDiv.textContent = "더 이상 단어가 없습니다.";
    }
}




// 다음 단어로 가기
function handleClickNext() {
    const card = document.getElementById("wordCard");
    if (currentIndex < words.length) {
        if (turning) {
            isAnimating = true;
            const wordDiv = document.getElementById("wordDisplay");
            wordDiv.textContent = " ";
            card.classList.add("fly-off");
            card.addEventListener('animationend', () => {
                currentIndex++;
                turning = false;
                card.classList.remove("fly-off");
                card.classList.remove("flipped");
                displayWord();
                saveCurrentIndex();
                isAnimating = false;
            }, { once: true });
        } else {
            isAnimating = true;
            card.classList.add("flipped");
            turning = true;
            isAnimating = false;
        }
    }
}




// 이전 단어로 가기
function handleClickPre() {
    if (currentIndex > 0) {
        currentIndex--;
        turning = false;
        const card = document.getElementById("wordCard");
        card.classList.remove("flipped");
        card.classList.remove("fly-off");
        displayWord();
        saveCurrentIndex();
    }
}




// 암기장에 저장하기
async function handleClickSave() {
    if (currentIndex < words.length) {
        const wordId = words[currentIndex].wordDTO.id;
        try {
            await fetch(`/api/userword/${wordId}`, { method: 'POST' });
            alert('암기장에 저장되었습니다.');
        } catch (error) {
            console.error('암기장 저장에 실패하였습니다.', error);
        }
    }
}