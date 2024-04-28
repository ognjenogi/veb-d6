// Define DOM elements
const postsContainer = document.getElementById('posts-container');
const postDetails = document.getElementById('post-details');
const newPostForm = document.getElementById('new-post-form');
const postsList = document.getElementById('posts-list');
const postTitle = document.getElementById('post-title');
const postAuthor = document.getElementById('post-author');
const postDate = document.getElementById('post-date');
const postContent = document.getElementById('post-content');
const commentsList = document.getElementById('comments-list');
const commentForm = document.getElementById('comment-form');
const newPostBtn = document.getElementById('new-post-btn');
const postTitleInput = document.getElementById('post-title-input');
const postContentInput = document.getElementById('post-content-input');
const commentTextInput = document.getElementById('comment-text');
// Function to handle login
let currentPostId = null;
function login(event) {
    event.preventDefault();
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const loginData = { username, password };

    fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to login');
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem('jwtToken', data.jwt);

            fetchPosts();

            hideElement(document.getElementById('login-container'));
            showElement(postsContainer);
        })
        .catch(error => {
            console.error('Error logging in:', error);
        });
}

function fetchPosts() {
    fetch('http://localhost:8080/api/posts', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            renderPosts(data);
        })
        .catch(error => {
            console.error('Error fetching posts:', error);
        });
}

// Function to render posts
function renderPosts(posts) {
    postsList.innerHTML = '';
    posts.forEach(post => {
        const li = document.createElement('li');
        const title = document.createElement('h3');
        title.textContent = post.title;
        const excerpt = document.createElement('p');
        excerpt.textContent = post.content.substring(0, 250) + '...';
        li.appendChild(title);
        li.appendChild(excerpt);
        li.addEventListener('click', () => {
            showPostDetails(post.id);
        });
        postsList.appendChild(li);
    });
}

// Function to show post details
function showPostDetails(postId) {
    fetch(`http://localhost:8080/api/posts/${postId}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(post => {
            currentPostId = post.id
            postTitle.textContent = post.title;
            postAuthor.textContent = post.author;
            postDate.textContent = post.date;
            postContent.textContent = post.content;
            renderComments(post.comments);
            hideElement(postsContainer);
            showElement(postDetails);
        })
        .catch(error => {
            console.error('Error fetching post details:', error);
        });
}

// Function to render comments
function renderComments(comments) {
    commentsList.innerHTML = '';
    comments.forEach(comment => {
        const li = document.createElement('li');
        li.textContent = `${comment.author}: ${comment.text}`;
        commentsList.appendChild(li);
    });
}

// Function to add a new comment
function addComment(event) {
    event.preventDefault();
    const text = commentTextInput.value.trim();
    if (text) {
        const commentData = {
            postId: currentPostId,
            author: null,
            text: text
        };
        fetch('http://localhost:8080/api/comment', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(commentData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to add comment');
                }
                return response.json();
            })
            .then(comment => {
                // Add the new comment to the UI
                const li = document.createElement('li');
                li.textContent = `${comment.author}: ${comment.text}`;
                commentsList.appendChild(li);

                // Clear the comment input fields
                commentTextInput.value = '';
            })
            .catch(error => {
                console.error('Error adding comment:', error);
            });
    }
}

// Function to show the new post form
function showNewPostForm() {
    hideElement(postsContainer);
    hideElement(postDetails);
    showElement(newPostForm);
}

// Function to create a new post
function createPost(event) {
    event.preventDefault();
    const title = postTitleInput.value.trim();
    const content = postContentInput.value.trim();
    if (title && content) {
        const postData = {
            title: title,
            author: null,
            content: content
        };

        fetch('http://localhost:8080/api/posts', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to create post');
                }
                return response.json();
            })
            .then(data => {
                // Add new post to UI
                const newPost = {
                    id: data.id,
                    title: postData.title,
                    author: postData.author,
                    content: postData.content,
                    date: new Date().toISOString().slice(0, 10)
                };
                renderPosts([newPost]);

                // Clear input fields
                postTitleInput.value = '';
                postContentInput.value = '';

                // Hide new post form and show posts container
                hideElement(newPostForm);
                showElement(postsContainer);
            })
            .catch(error => {
                console.error('Error creating post:', error);
            });
    }
}

// Function to show an element
function showElement(element) {
    element.classList.remove('hidden');
}

// Function to hide an element
function hideElement(element) {
    element.classList.add('hidden');
}

// Add event listeners
document.getElementById('login-form').addEventListener('submit', login);
commentForm.addEventListener('submit', addComment);
newPostBtn.addEventListener('click', showNewPostForm);
newPostForm.addEventListener('submit', createPost);

// Fetch posts when the page loads
window.addEventListener('load', () => {
    showElement(document.getElementById('login-container'));
    hideElement(postsContainer);
});
