const registrationForm = document.querySelector('.reg__form')
const regUsernameInput = registrationForm.querySelector('#usernameInput')
const regEmailInput = registrationForm.querySelector('#emailInput')
const regMobileInput = registrationForm.querySelector('#mobileInput')

const messagesWrapper = document.querySelector('.messages__wrapper')

registrationForm.addEventListener('submit', submitRegistrationForm)

function submitRegistrationForm(event) {
    event.preventDefault()

    const username = regUsernameInput.value.trim();
    const email = regEmailInput.value.trim();
    const mobile = regMobileInput.value.trim();

    const user = {
        username: username,
        email: email,
        mobile: mobile
    }

    $.ajax({
        url: '/auth/register',
        type: 'POST',
        data: JSON.stringify(user),
        contentType: 'application/json',
        success: function () {
            addSuccessMessages([`User '${username}' has been registered successfully`])
        },
        error: function (response) {
            addErrorMessages(response.responseJSON.messages)
        }
    })
}

function addSuccessMessages(messages) {
    addMessages(messages, 'success')
}

function addErrorMessages(messages) {
    addMessages(messages, 'error')
}

function addMessages(messages, type) {
    messages.forEach(message => {
        const messageDiv = document.createElement("div")
        messageDiv.classList.add('message', `message-${type}`)
        messageDiv.innerText = message

        messagesWrapper.appendChild(messageDiv)
        setTimeout(() => messageDiv.remove(), 5000)
    })
}
