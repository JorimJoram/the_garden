window.onload = function(){
    const eventSource = new EventSource("/subscribe");

    eventSource.addEventListener("dailyNotification", function(event){
        const notificationDiv = document.getElementById('notifications')
        const newNotification = document.createElement("p")
        newNotification.textContent = event.data;
        notificationDiv.appendChild(newNotification);
    });

    eventSource.error = function(){
        console.error("EventSource failed");
        eventSource.close();
    }
}