function markAsRead(notificationId) {
    fetch('/notifications', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: `action=markRead&notificationId=${notificationId}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            document.querySelector(`[data-id="${notificationId}"]`).classList.remove('unread');
            updateUnreadCount();
        }
    });
}

function markAllAsRead() {
    fetch('/notifications', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: 'action=markAllRead'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            document.querySelectorAll('.unread').forEach(el => {
                el.classList.remove('unread');
            });
            updateUnreadCount();
        }
    });
}

function deleteNotification(notificationId) {
    if (!confirm('Are you sure you want to delete this notification?')) {
        return;
    }

    fetch('/notifications', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: `action=delete&notificationId=${notificationId}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            const element = document.querySelector(`[data-id="${notificationId}"]`);
            element.remove();
            updateUnreadCount();
        }
    });
}

function updateUnreadCount() {
    fetch('/notifications/count')
        .then(response => response.json())
        .then(data => {
            // Update any UI elements showing unread count
            const countElement = document.querySelector('.notification-count');
            if (countElement) {
                countElement.textContent = data.count;
                countElement.style.display = data.count > 0 ? 'block' : 'none';
            }
        });
}

// Poll for new notifications every minute
setInterval(updateUnreadCount, 60000); 