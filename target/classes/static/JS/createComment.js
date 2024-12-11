document.addEventListener("DOMContentLoaded", () => {
    const likeButton = document.querySelector(".like-button");
    const dislikeButton = document.querySelector(".dislike-button");
    const videoElement = document.querySelector(".video-player-container");
    const videoId = parseInt(videoElement.getAttribute("data-video-id"), 10);
    const likeCountSpan = document.getElementById("like-count");
    const dislikeCountSpan = document.getElementById("dislike-count");

    if (isNaN(videoId)) {
        console.error("Invalid video ID");
        return;
    }

    const updateCount = async (url, element) => {
        try {
            const response = await fetch(url, { method: "POST" });
            if (response.ok) {
                const newCount = await response.json();
                element.textContent = newCount;
            } else {
                console.error("Failed to update count:", response.statusText);
            }
        } catch (error) {
            console.error("Error updating count:", error);
        }
    };

    likeButton.addEventListener("click", () => {
        updateCount(`/videos/${videoId}/like`, likeCountSpan);
    });

    dislikeButton.addEventListener("click", () => {
        updateCount(`/videos/${videoId}/dislike`, dislikeCountSpan);
    });
});