

document.addEventListener('click',(e)=>{
        // Retrieve id from clicked element
        let elementId = e.target.id;
        // If element has id
        if (elementId !== '') {
            console.log(elementId);
        }
        // If element has no id
        else { 
            console.log("An element without an e was clicked.");
        }
});

