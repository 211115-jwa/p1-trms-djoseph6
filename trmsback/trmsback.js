/*
    MUST BE FILLED IN TO CUSTOMIZE EXAMPLE
*/
// Endpoint you are sending a GET request to
let apiURL = 'http://localhost:8080/trmsback/requests';
let apiURL1 = 'http://localhost:8080/trmsback/requestor/{id}';

document.getElementById('getData').onclick = getData;

async function getData() {
    // If using input for identifiers, etc.
    // For example, if using PokeAPI, this may be the Pokemon's ID.
    let userInput = document.getElementById('dataInput').elements; 
    let obj ={};
    for(var i = 1 ; i < userInput.length ; i++){
        var item = userInput.item(i);
        obj[item.name] = item.value;
            }
            console.log(obj);
       
        let response = await fetch(apiURL + userInput,
            {
                method:'PUT',
                body:JSON.stringify(obj)
            });

        if (response.status === 200) {
            let data = await response.json();
            populateData(data);
        } else {
            console.error();

        
        }
}

function populateData(response) {
    let dataSection = document.getElementById('data');
    /*
        Process data from the API to display on the page.
    */

}

document.getElementById('getButton').onclick = getReimbursementRequestsByEmpID();

async function getReimbursementRequestsByEmpID() {
    let userInput1 = document.getElementById("supervisorid");

     
        let response = await fetch(apiURL + userInput1);

        if (response.status === 200) {
            let setReimRequests = await response.json();
            showRequests(setReimRequests);
        } else {
            console.error();
            
        }
    
}
function showRequests(setReimRequests) {
    let reimbursementTable = document.getElementById('reimbursementByEmpID');

    for (let reimRequest of setReimRequests) {
        let rowForRequest = document.createElement('tr');

        for (let field in reimRequest) {
            let column = document.createElement('td');
            if (field!=='status') {
                column.innerText = reimRequest[field];
            }
            rowForRequest.appendChild(column);
        }
        reimbursementTable.appendChild(rowForRequest);
    }
}
