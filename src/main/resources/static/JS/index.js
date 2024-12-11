document.addEventListener("DOMContentLoaded", () => {
    const likeButton = document.querySelector(".like-button");
    const dislikeButton = document.querySelector(".dislike-button");
    const videoElement = document.querySelector(".video-player-container");
    const videoId = parseInt(videoElement.getAttribute("data-video-id"), 10);
    const likeCountSpan = document.getElementById("like-count");
    const dislikeCountSpan = document.getElementById("dislike-count");

    const updateCount = (url, element) => {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const newCount = JSON.parse(xhr.responseText);
                element.textContent = newCount;
            }
        };
        xhr.send();
    };

    likeButton.addEventListener("click", () => {
        updateCount(`/videos/${videoId}/like`, likeCountSpan);
    });

    dislikeButton.addEventListener("click", () => {
        updateCount(`/videos/${videoId}/dislike`, dislikeCountSpan);
    });
});