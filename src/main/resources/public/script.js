document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('decisionForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const clientId = document.getElementById('clientId').value;
        const amount = document.getElementById('amount').value;
        const month = document.getElementById('month').value;

        const requestDecision = {
            clientId: clientId,
            amount: parseFloat(amount),
            month: parseFloat(month)
        };

        fetch('/getDecision', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestDecision)
            })
            .then(response => response.json())
            .then(data => {
                const responseContainer = document.getElementById('responseContainer');
                console.log(data)
                if (data.validationErrors) {
                    let responseInvalidAmount = ""
                    let responseInvalidMonth = ""
                    if (data.validationErrors.amount) {
                        responseInvalidAmount = `<p> Wrong data in amount : ${data.validationErrors.amount}</p>`;
                    }
                    if (data.validationErrors.month) {
                        responseInvalidMonth = `<p> Wrong data in month : ${data.validationErrors.month}</p>`;
                    }
                    responseContainer.innerHTML = responseInvalidAmount + responseInvalidMonth;
                } else if (data.amount && data.period && data.status) {
                    const responseHtml = `
                    <p>Amount: ${data.amount}</p>
                    <p>Period: ${data.period}</p>
                    <p>Status: ${data.status}</p>
                `;
                    responseContainer.innerHTML = responseHtml;
                } else {
                    responseContainer.innerHTML = `<p>Status: ${data.status}</p>`;
                }
            })
            .catch(error => {
                console.error('Error:', error);
                const responseContainer = document.getElementById('responseContainer');
                responseContainer.innerHTML = `<p>Error: ${error.message}</p>`;
            });
    });
});