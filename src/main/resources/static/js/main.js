/**
 * UTECommerce Main JavaScript
 */
document.addEventListener("DOMContentLoaded", function () {
    console.log("UTECommerce initialized successfully.");

    // Initialize Bootstrap tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Setup WebSocket connection (STOMP over SockJS)
    connectWebSocket();
});

function connectWebSocket() {
    try {
        const socket = new SockJS('/ws-utecommerce');
        const stompClient = Stomp.over(socket);
        stompClient.debug = null; // Tắt log debug chi tiết

        stompClient.connect({}, function (frame) {
            console.log("WebSocket connected: " + frame);

            // Đăng ký nhận thông báo chung của hệ thống
            stompClient.subscribe('/topic/notifications', function (response) {
                showToast(response.body);
            });
        }, function (error) {
            console.log("WebSocket connection error (Server might not be running yet):", error);
        });
    } catch (e) {
        console.warn("WebSocket could not be initialized:", e);
    }
}

function showToast(message) {
    const toastEl = document.getElementById('liveToast');
    const toastMsgEl = document.getElementById('toastMessage');
    if (toastEl && toastMsgEl) {
        toastMsgEl.innerText = message;
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
    }
}

