let getFlag, getType;
let currentIndex = 0;
let words = [];
let turning = false;
let extending = false;
let isAnimating = false;
let currentCard = 'card1';
let nextCard = 'card2';

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

        // 암기장일 때 삭제 버튼 활성화
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
        updateLoadingBar(70); // 첫 번째 요청 완료 후 70%로 설정

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
        updateLoadingBar(90); // 두 번째 요청 완료 후 90%로 설정

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

        // 애니메이션 중이면 단축키 동작 안 함
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

    // 페이드아웃 애니메이션 진행
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

    // 단어의 id 값을 인덱스로 사용
    const wordId = words[currentIndex] ? words[currentIndex].wordDTO.id : 0;

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

    // 현재 카드 요소 가져오기
    const card = document.getElementById(currentCard);
    const cardFrontSecond = document.getElementById(`${currentCard}FrontSecond`);
    const backDiv = document.getElementById(`${currentCard}Back`);

    // 현재 카드 보이기 및 z-index 설정
    card.classList.remove('hidden');
    card.classList.remove('opacity-0');
    card.style.zIndex = '2';

    // 다음 카드 숨기기 및 z-index 설정
    const nextCardElement = document.getElementById(nextCard);
    nextCardElement.classList.add('hidden');
    nextCardElement.style.zIndex = '1';

    // 카드 뒤집힘 상태 초기화
    card.classList.remove('flipped');

    // JWT 토큰 가져오기
    const accessToken = localStorage.getItem('accessToken');

    // 로그인 상태 확인
    if (!accessToken) {
        cardFrontSecond.style.fontSize = '2rem';
        backDiv.style.fontSize = '2rem';
        
        cardFrontSecond.textContent = "로그인이 필요한 서비스입니다.";
        backDiv.textContent = "로그인이 필요한 서비스입니다.";

        return;
    }

    // 카드 카운팅 업데이트
    cardCounting();



    // mean들 준비하기
    const means = [];

    for (let i = 1; i <= 3; i++) {
        const meanField = `mean${i}`;

        const mean = words[currentIndex].wordDTO[meanField];

        // 둘다 존재할때만 배열에 넣기
        if (mean) {
            means.push(mean);
        } else {
            break; // 필드가 없으면 반복 종료
        }
    }

    // eword와 emean들 준비하기
    const ewords = [];
    const emeans = [];

    for (let i = 1; i <= 5; i++) {
        const ewordField = `eword${i}`;
        const emeanField = `emean${i}`;

        const eword = words[currentIndex].wordDTO[ewordField];
        const emean = words[currentIndex].wordDTO[emeanField];

        // 둘다 존재할때만 배열에 넣기
        if (eword && emean) {
            ewords.push(eword);
            emeans.push(emean);
        } else {
            break; // 필드가 없으면 반복 종료
        }
    }


    if (currentIndex < words.length && currentIndex >= 0) {

        // 초기화
        cardFrontSecond.innerHTML = '';
        backDiv.innerHTML = '';

        const word = cleanEnglishWord(words[currentIndex].wordDTO.word);
        const meaning = words[currentIndex].wordDTO.mean1;
        

        // 글자 길이에 따라 폰트 크기 조정
        if (word.length > 10) {
            cardFrontSecond.style.fontSize = '2rem';
        } else if (word.length > 6) {
            cardFrontSecond.style.fontSize = '3rem';
        } else if (word.length > 4) {
            cardFrontSecond.style.fontSize = '4rem';
        } else if (word.length > 2) {
            cardFrontSecond.style.fontSize = '5rem';
        } else {
            cardFrontSecond.style.fontSize = '10rem';
        }

        // 앞카드에 단어 넣기
        cardFrontSecond.textContent = word;


        // 뒷카드에 요소(부수) 넣기
        const backEwordEmeanContainer = document.createElement('div');
        backEwordEmeanContainer.className = 'flex items-center justify-evenly';

        for (let i = 0; i < ewords.length; i++) {

            const epairDiv = document.createElement('div');
            epairDiv.className = 'epair';

            const ewordDiv = document.createElement('div');
            ewordDiv.className = 'eword';
            ewordDiv.textContent = ewords[i];
            
            const emeanDiv = document.createElement('div');
            emeanDiv.className = 'emean';
            emeanDiv.textContent = emeans[i];
            
            epairDiv.appendChild(ewordDiv);
            epairDiv.appendChild(emeanDiv);

            backEwordEmeanContainer.appendChild(epairDiv);
        }
        
        backDiv.appendChild(backEwordEmeanContainer);


        // 뒷카드에 뜻 넣기
        for (let i = 0; i < means.length; i++) {

            const backMeanDiv = document.createElement('div');
            
            backMeanDiv.className = 'backMean'
            backMeanDiv.classList.add('pt-3');
            backMeanDiv.textContent = means[i];
            backDiv.appendChild(backMeanDiv);
        }

        
        // 뒷카드 뜻이 여러개면 폰트 작게
        if (means.length > 2) {
            const backMeanDivs = backDiv.querySelectorAll('.backMean');

            backMeanDivs.forEach(div => {
                div.style.fontSize = '1.2rem';
            });
        }
        else if(means.length > 1) {
            const backMeanDivs = backDiv.querySelectorAll('.backMean');
    
            backMeanDivs.forEach(div => {
                div.style.fontSize = '1.8rem';
            });
        }
        else { // 뒷카드 뜻이 한개면 폰트 세분화
            const backMeanDiv = backDiv.querySelector('.backMean');

            if (means[0].length > 10) {
                backMeanDiv.style.fontSize = '2rem';
            } else if (means[0].length > 6) {
                backMeanDiv.style.fontSize = '3rem';
            } else if (means[0].length > 4) {
                backMeanDiv.style.fontSize = '4rem';
            } else {
                backMeanDiv.style.fontSize = '5rem';
            }
        }



    } else {
        // 단어가 없을 때 폰트 크기 설정
        cardFrontSecond.style.fontSize = '2rem';
        backDiv.style.fontSize = '2rem';
        
        cardFrontSecond.textContent = "더 이상 단어가 없습니다.";
        backDiv.textContent = "더 이상 단어가 없습니다.";
    }
}

// 다음 단어로 이동
function handleClickNext() {

    if (isAnimating) return;

    const card = document.getElementById(currentCard);

    if (currentIndex < words.length) {
        if (turning) {
            isAnimating = true;

            // 카드 날아가는 애니메이션 추가
            card.classList.add('fly-off');

            // 애니메이션동안 다음 카드의 내용 잠시 비우기
            const cardFrontSecond = document.getElementById(`${currentCard}FrontSecond`);
            const backDiv = document.getElementById(`${currentCard}Back`);
            cardFrontSecond.textContent = " ";
            backDiv.textContent = " ";


            card.addEventListener('animationend', () => {

                // 애니메이션 종료 후 초기화
                card.classList.add('hidden');
                card.classList.add('opacity-0');
                card.classList.remove('fly-off');
                card.classList.remove('flipped');

                // 카드 위치 초기화
                card.classList.remove('opacity-0');

                setTimeout(()=>{
                    // 다음 카드 준비
                    currentIndex++;
                    
                    // currentCard와 nextCard 교체
                    [currentCard, nextCard] = [nextCard, currentCard];

                    displayWord();
                    isAnimating = false;
                    turning = false;
                }, 200);

                // 현재 인덱스 저장
                saveCurrentIndex();

            }, { once: true });

        } else {
            // 카드 뒤집기
            isAnimating = true;
            card.classList.add('flipped');
            turning = true;
            isAnimating = false;
        }
    }
}

// 이전 단어로 이동
function handleClickPre() {

    if (isAnimating) return;

    const card = document.getElementById(currentCard);

    if (currentIndex === 0 && turning) {
        // 첫 번째 카드에서 뒤집혀 있을 경우, 뒤집기 취소
        isAnimating = true;
        card.classList.remove('flipped');
        turning = false;
        isAnimating = false;
        return;
    }

    if (currentIndex > 0) {
        if (turning) {
            // 카드 뒤집기 취소
            isAnimating = true;
            card.classList.remove('flipped');
            turning = false;
            isAnimating = false;
        } else {
            // 이전 단어로 이동
            currentIndex--;
            turning = false;
            isAnimating = true;

            // currentCard와 nextCard 교체
            [currentCard, nextCard] = [nextCard, currentCard];

            // 이전 카드 준비
            displayWord();

            isAnimating = false;

            // 현재 인덱스 저장
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

        // 단어 섞기 요청
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

    if (currentIndex >= words.length) {
        countDiv.textContent = "finish";
    } else {
        countDiv.textContent = `${currentIndex + 1} / ${words.length}`;
    }
}


// 문자 포맷
function cleanEnglishWord(input) {

    return input
        .replace(/\|[^|]*\|/g, '') // `|` 로 둘러쌓인 단어 제거
        .replace(/[·()]/g, '');   // `·`, `(`, `)` 제거
}