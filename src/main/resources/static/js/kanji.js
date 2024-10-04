let currentIndex = 0;
let words = [];
let turning = false;
let isAnimating = false;

async function loadWords() {
    try {
        const response = await fetch('/api/userword?type=KANJI');
        words = await response.json();
        displayWord();
    } catch (error) {
        console.error('데이터를 불러오는데 실패하였습니다.', error);
    }

    document.addEventListener('keydown', function (event) {
        if (isAnimating) return; // 애니메이션 중이면 동작 안함

        if (event.key === 'Enter') {
            handleClickNext();
        } else if (event.key === 'p') {
            handleClickSave();
        } else if (event.key === 'Backspace') {
            handleClickPre();
        }
    });
}

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
        wordDiv.textContent = "더이상 단어가 없습니다.";
        meaningDiv.textContent = "더이상 단어가 없습니다.";
    }
}

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

function handleClickPre() {
    if (currentIndex > 0) {
        currentIndex--;
        turning = false;
        const card = document.getElementById("wordCard");
        card.classList.remove("flipped");
        card.classList.remove("fly-off");
        displayWord();
    }
}

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

window.onload = loadWords;