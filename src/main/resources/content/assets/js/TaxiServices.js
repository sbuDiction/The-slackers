const displayTemplateOne = document.querySelector('.templateOne');
const destinatioCompiler = Handlebars.compile(displayTemplateOne.innerHTML);
const templateOne = document.querySelector('.destinationTemplate');

const firstName = document.querySelector('.name');
const amount = document.querySelector('.cash');
const calculateBtn = document.querySelector('.bookBtn');

const displayDestinationDropDown = () => {
    axios.get('/api/v1/destinations').then((response) => {
        let results = response.data;
        console.log(results);
        let display = destinatioCompiler({ destinations: results })
        templateOne.innerHTML = display;
    })
}

window.onload = () => {
    displayDestinationDropDown();
}



//calculateBtn.addEventListener('click', ()=>{
//    async = () => {
//        let params = {
//            "firstname": firstName,
//            "amount": amount
//        }
//
//       axios.post('/book', params).then((results => {
//            let status = results.status;
//            console.log(status);
//
//        }))
//    }
//})