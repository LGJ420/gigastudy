let getFlag, getType;
let currentIndex = 0;
let words = [];
let turning = false;
let isAnimating = false;

// 사이트 초기화
async function init(flag, type) {

    showLoadingScreen("단어를 불러오는 중입니다...");

    try {
        getType = type;

        if (flag === null) {
            getFlag = false;
        } else {
            getFlag = flag
        }

        // 암기장일때는 삭제버튼 활성화
        const deleteButton = document.getElementById('deleteButton');

        if (flag === true) {
            deleteButton.classList.remove('hidden');
        }

        updateLoadingBar(30);




        // JWT 토큰 가져오기
        const accessToken = localStorage.getItem('accessToken');

        // currentIndex 불러오기
        const indexResponse = await fetch(`/api/userindex?flag=${getFlag}&type=${type}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        currentIndex = await indexResponse.json();
        updateLoadingBar(70); // 첫번째 요청 완료 후 50%로 설정





        // 단어 리스트 불러오기
        let wordsUrl = `/api/word?type=${type}`;

        if (flag !== null) {
            wordsUrl += `&flag=${flag}`;
        }

        const response = await fetch(wordsUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        words = await response.json();
        updateLoadingBar(90); // 두번째 요청 완료 후 90%로 설정






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

        if (event.code === 'ArrowRight' || event.code === 'Enter' || event.code === 'KeyD') {
            handleClickNext();
        }
        else if (event.code === 'ArrowLeft' || event.code === 'Backspace' || event.code === 'KeyA') {
            handleClickPre();
        }
        else if (event.code === 'ArrowUp' || event.code === 'Quote' || event.code === 'KeyW') {
            handleClickSave();
        }
        else if (event.code === 'ArrowDown' || event.code === 'Backslash' || event.code === 'KeyS') {
            handleClickDelete();
        }
    });
}




// 로딩 화면 표시
function showLoadingScreen(message) {
    const loadingBarContainer = document.getElementById("loadingBarContainer");
    loadingBarContainer.style.opacity = '1';
    loadingBarContainer.classList.remove('hidden');

    const loadingMessage = document.getElementById("loadingMessage");
    loadingMessage.innerText = message;

    // 로딩 바 초기화
    updateLoadingBar(0);
}




// 로딩 화면 페이드아웃
function fadeOutLoadingScreen() {
    const loadingBarContainer = document.getElementById("loadingBarContainer");

    // 페이드아웃 애니메이션이 진행됨
    loadingBarContainer.style.opacity = '0';

    // 페이드아웃 애니메이션이 끝난 후 로딩바를 제거
    setTimeout(() => {
        loadingBarContainer.classList.add('hidden');
    }, 500);
}




// 로딩바 업데이트
function updateLoadingBar(percent) {
    const loadingBar = document.getElementById("loadingBar");
    loadingBar.style.width = percent + "%";
}




// 인덱스 저장
async function saveCurrentIndex() {

    // 단어의 id값을 인덱스로 사용한다.
    const wordId = words[currentIndex].wordDTO.id;

    try {
        // JWT 토큰 가져오기
        const accessToken = localStorage.getItem('accessToken');

        await fetch('/api/userindex', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                flag: getFlag,
                type: getType,
                saveIndex: wordId
            })
        });
        console.log('인덱스가 저장되었습니다.');
    } catch (error) {
        console.error('인덱스 저장에 실패하였습니다.', error);
    }
}




// 단어카드 보여주기
function displayWord() {

    // 가렸었던 카드 보이기
    const card = document.getElementById("wordCard");
    card.classList.remove("opacity-0");

    const wordDiv = document.getElementById("wordDisplay");
    const meaningDiv = document.getElementById("wordMeaning");

    // JWT 토큰 가져오기
    const accessToken = localStorage.getItem('accessToken');

    // 로그인 상태 확인
    if (!accessToken) {
        wordDiv.style.fontSize = '2rem';
        meaningDiv.style.fontSize = '2rem';
        
        wordDiv.textContent = "로그인이 필요한 서비스입니다.";
        meaningDiv.textContent = "로그인이 필요한 서비스입니다.";

        return;
    }

    // 화면에 카운트 표시
    cardCounting();

    if (currentIndex < words.length && currentIndex >= 0) {
        const word = words[currentIndex].wordDTO.word;
        const meaning = words[currentIndex].wordDTO.mean1;

        // 글자 길이에 따라 폰트 크기를 조정
        if (word.length > 10) {
            wordDiv.style.fontSize = '2rem';
        } else if (word.length > 6) {
            wordDiv.style.fontSize = '3rem';
        } else if (word.length > 4) {
            wordDiv.style.fontSize = '4rem';
        } else if (word.length > 2) {
            wordDiv.style.fontSize = '5rem';
        } else if (word.length > 1) {
            wordDiv.style.fontSize = '7rem';
        } else {
            wordDiv.style.fontSize = '10rem';
        }

        if (meaning.length > 10) {
            meaningDiv.style.fontSize = '2rem';
        } else if (meaning.length > 6) {
            meaningDiv.style.fontSize = '3rem';
        } else if (meaning.length > 4) {
            meaningDiv.style.fontSize = '4rem';
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
            const meaningDiv = document.getElementById("wordMeaning");
            wordDiv.textContent = " ";
            meaningDiv.textContent = " ";
            card.classList.add("fly-off");
            card.addEventListener('animationend', () => {
                currentIndex++;
                turning = false;
                card.classList.add("opacity-0");
                card.classList.remove("fly-off");
                card.classList.remove("flipped");
                saveCurrentIndex();
                
                setTimeout(()=>{
                    displayWord();
                    isAnimating = false;
                }, 200);
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

    const card = document.getElementById("wordCard");

    if (currentIndex === 0 && turning) {
        isAnimating = true;
        card.classList.remove("flipped");
        turning = false;
        isAnimating = false;
    }

    if (currentIndex > 0) {
        if (turning) {
            isAnimating = true;
            card.classList.remove("flipped");
            turning = false;
            isAnimating = false;
        } else {
            currentIndex--;
            turning = false;
            card.classList.remove("flipped");
            card.classList.remove("fly-off");
            displayWord();
            saveCurrentIndex();
        }
    }
}




// 암기장에 단어 저장하기
async function handleClickSave() {
    if (currentIndex < words.length) {
        const wordId = words[currentIndex].wordDTO.id;
        try {
            // JWT 토큰 가져오기
            const accessToken = localStorage.getItem('accessToken');

            await fetch(`/api/word/${wordId}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                }
            });
            showAlert('암기장에 저장되었습니다.')
        } catch (error) {
            console.error('암기장 저장에 실패하였습니다.', error);
        }
    }
}




// 암기장에서 단어 삭제하기
async function handleClickDelete() {
    if (currentIndex < words.length) {
        const wordId = words[currentIndex].wordDTO.id;
        try {
            // JWT 토큰 가져오기
            const accessToken = localStorage.getItem('accessToken');

            await fetch(`/api/word/${wordId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                }
            });
            showAlert('암기장에서 삭제되었습니다.')
        } catch (error) {
            console.error('암기장 삭제에 실패하였습니다.', error);
        }
    }
}




// 단어 섞기
async function handleClickShuffle() {
    try {
        // 로딩 화면 표시
        showLoadingScreen("단어를 섞는 중입니다...");
        updateLoadingBar(70);

        // JWT 토큰 가져오기
        const accessToken = localStorage.getItem('accessToken');


        let wordsUrl = `/api/word/shuffle?type=${getType}`;

        if (getFlag !== null) {
            wordsUrl += `&flag=${getFlag}`;
        }

        // 단어 섞기 요청 보내기
        await fetch(wordsUrl, {
            method: 'POST',
            headers: {
               'Authorization': `Bearer ${accessToken}`
            }
        });
        console.log('단어 섞기가 완료되었습니다.');
        updateLoadingBar(100);

        setTimeout(() => {
            window.location.reload();
        }, 1200);

    } catch (error) {
        console.error('단어 섞기에 실패하였습니다.', error);
        updateLoadingBar(100);

        setTimeout(() => {
            fadeOutLoadingScreen();
        }, 500);
    }
}




// 처음으로 이동
function handleClickFirst(){

    currentIndex = 0;
    displayWord();
    saveCurrentIndex();
}




// 카드 카운팅
function cardCounting(){

    const countDiv = document.getElementById("cardCount");

    if (currentIndex === words.length) {
    
        countDiv.textContent = "finish"
    }
    else {

        countDiv.textContent = `${currentIndex + 1} / ${words.length}`
    }
}