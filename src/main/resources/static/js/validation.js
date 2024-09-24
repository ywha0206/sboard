
    // 유효성 검사에 사용할 정규표현식
    const reUid   = /^[a-z]+[a-z0-9]{4,19}$/g;
    const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
    const reName  = /^[가-힣]{2,10}$/
    const reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
    const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

    // 유효성 검사에 사용할 상태변수
    let isUidOk   = false;
    let isPassOk  = false;
    let isNameOk  = false;
    let isNickOk  = false;
    let isEmailOk = false;
    let isHpOk    = false;

    function postcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                //document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zip').value = data.zonecode;
            document.getElementById("addr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addr2").focus();
        }
    }).open();
}

    document.addEventListener('DOMContentLoaded', function() {

    const post= document.getElementById('post')
    post.addEventListener('click',function (){
    postcode();
})

    const btnCheckUid = document.getElementById('btnCheckUid');
    const btnCheckNick = document.getElementById('btnCheckNick');
    const btnSendEmail = document.getElementById('btnSendEmail');
    const btnAuthEmail = document.getElementById('btnAuthEmail');

    const registerForm = document.getElementsByTagName('form')[0];

    const resultId = document.getElementsByClassName('resultId')[0];
    const resultPass = document.getElementsByClassName('resultPass')[0];
    const resultName = document.getElementsByClassName('resultName')[0];
    const resultNick = document.getElementsByClassName('resultNick')[0];
    const resultEmail = document.getElementsByClassName('resultEmail')[0];
    const resultHp = document.getElementsByClassName('resultHp')[0];
    const auth = document.getElementsByClassName('auth')[0];


    // 1.아이디 유효성 검사
    btnCheckUid.onclick = function() {

    const uid = registerForm.uid.value;

    // 아이디 유효성 검사
    if (!uid.match(reUid)) {
    resultId.innerText = '아이디가 유효하지 않습니다.';
    resultId.style.color = 'red';
    return;
}
    checkUserIdAvailability(uid);
}
    // 중복체크
    function checkUserIdAvailability(uid) {
    console.log(uid);
    fetch('/sboard/user/checkUser?uid=' + uid)
    .then(response => {
    if (!response.ok) {
    throw new Error('서버 응답 오류');
}
    return response.json();
})
    .then(data => {
    console.log(data)
    if (data) {
    resultId.innerText ='이미 사용중인 아이디입니다.';
    resultId.style.color = 'red';
    isUidOk = false;
} else {
    resultId.innerText ='사용 가능한 아이디입니다.';
    resultId.style.color = 'green';
    isUidOk = true;
}
})
    .catch(error => {
    console.error('에러:', error);
    showResult('아이디 확인 중 오류가 발생했습니다.', 'red');
});
}


    // 2.비밀번호 유효성 검사
    registerForm.pass2.addEventListener('focusout', function(){

    const pass = registerForm.pass.value;
    const pass2 = registerForm.pass2.value;

    if(!pass.match(rePass)){
    resultPass.innerText = "비밀번호가 유효하지 않습니다.";
    resultPass.style.color = 'red';
    return;
}

    if(pass == pass2){
    resultPass.innerText = "비밀번호가 일치합니다.";
    resultPass.style.color = 'green';
    isPassOk = true;
}else{
    resultPass.innerText = "비밀번호가 일치하지 않습니다.";
    resultPass.style.color = 'red';
    isPassOk = false;
}
});

    // 3.이름 유효성 검사
    registerForm.name.addEventListener('focusout', function(){

    const name = registerForm.name.value;

    if(!name.match(reName)){
    resultName.innerText = "이름이 유효하지 않습니다.";
    resultName.style.color = 'red';
    isNameOk = false;
}else{
    resultName.innerText = "";
    isNameOk = true;
}
});

    // 4.별명 유효성 검사
    btnCheckNick.addEventListener('click',function (){
    const nick = registerForm.nick.value;
    const nickPram = encodeURI(encodeURIComponent(nick));

    if(!nick.match(reNick)){
    resultNick.innerText = '별명이 유효하지 않습니다.';
    resultNick.style.color = 'red';
    return;
}else{
    resultNick.innerText = '별명이 유효합니다.';
}
    console.log(nick);
    checkUserNick(nickPram);
})
    function checkUserNick(nick){
    fetch('/sboard/user/checkUserNick?nick='+nick)
    .then(response => {
    if (!response.ok) {
    throw new Error('서버 응답 오류');
}
    return response.json();
})
    .then(data => {
    console.log("data"+data);
    if(data.result > 0){
    resultNick.innerText = '이미 사용중인 별명입니다.';
    resultNick.style.color = 'red';
    isNickOk = false;
}else{
    resultNick.innerText = '사용 가능한 별명입니다.';
    resultNick.style.color = 'green';
    isNickOk = true;
}
}).catch(err => {
    console.log(err);
});


}


    // 5.이메일 유효성 검사
    let preventDblClick = false;

    btnSendEmail.onclick = async function(){

    const email = registerForm.email.value;
    console.log(email)
    // 이중 클릭 방지
    if(preventDblClick){
    return;
}

    // 이메일 유효성 검사
    if(!email.match(reEmail)){
    resultEmail.innerText = '유효한 이메일이 아닙니다.';
    resultEmail.style.color = 'red';
    return;
}else{
    resultEmail.innerText = '유효한 이메일입니다.';
    resultEmail.style.color = 'green';
}

    try{
    preventDblClick = true;

    const response = await fetch('/sboard/user/checkUserEmail?email='+email);
    const data = await response.json();
    console.log(data);

    if(data){
    resultEmail.innerText = '이미 사용중인 이메일 입니다.';
    resultEmail.style.color = 'red';
    isEmailOk = false;
}else{
    resultEmail.innerText = '이메일 인증코드를 확인 하세요.';
    resultEmail.style.color = 'green';
    auth.style.display = 'block';
}


}catch(e){
    console.log(e);
}
}

    btnAuthEmail.onclick = function(){

    const code = registerForm.auth.value;

    fetch('/sboard/user/checkUserEmail', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({"code":code})
})
    .then(resp => resp.json())
    .then(data => {
    console.log(data);

    if(data.result > 0){
    resultEmail.innerText = '이메일이 인증되었습니다.';
    resultEmail.style.color = 'green';
    isEmailOk = true;
}else{
    resultEmail.innerText = `유효한 인증코드가 아닙니다.${code}`;
    resultEmail.style.color = 'red';
    isEmailOk = false;
}
})
    .catch(err => {
    console.log(err);
});


}




    // 6.휴대폰 유효성 검사
    registerForm.hp.addEventListener('focusout', async function(){

    const hp = registerForm.hp.value;
    if(!hp.match(reHp)){
    resultHp.innerText = '전화번호가 유효하지 않습니다.';
    resultHp.style.color = 'red';
    return;
}

    try{
    const response = await fetch('/sboard/user/checkUserHp?hp='+hp);
    const data = await response.json();
    console.log(data);

    if(data.result > 0){
    resultHp.innerText = '이미 사용중인 휴대폰번호 입니다.';
    resultHp.style.color = 'red';
    isHpOk = false;
}else{
    resultHp.innerText = '사용할 수 있는 번호입니다.';
    resultHp.style.color = 'green';
    isHpOk = true;
}

}catch(err){
    console.log(err);
}
});


    // 최종 폼 전송 유효성 검사
    registerForm.onsubmit = function(){

    // 아이디 유효성 검사 완료 여부
    if(!isUidOk){
    alert('아이디가 유효하지 않습니다.');
    return false; // 폼 전송 취소
}

    // 비밀번호 유효성 검사 완료 여부
    if(!isPassOk){
    alert('비밀번호가 유효하지 않습니다.');
    return false; // 폼 전송 취소
}

    // 이름 유효성 검사 완료 여부
    if(!isNameOk){
    alert('이름이 유효하지 않습니다.');
    return false; // 폼 전송 취소
}

    // 별명 유효성 검사 완료 여부
    if(!isNickOk){
    alert('별명이 유효하지 않습니다.');
    return false; // 폼 전송 취소
}

    // 이메일 유효성 검사 완료 여부
    if(!isEmailOk){
    alert('이메일이 유효하지 않습니다.');
    return false; // 폼 전송 취소
}

    // 휴대폰 유효성 검사 완료 여부
    if(!isHpOk){
    alert('휴대폰 번호가 유효하지 않습니다.');
    return false; // 폼 전송 취소
}

    return true; // 폼 전송
}


})
