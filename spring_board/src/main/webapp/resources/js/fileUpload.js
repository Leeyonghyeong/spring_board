const fileDropZone = document.querySelector(".dropZone");
const fileDelete = document.querySelector(".bi-x-circle-fill");
const form = document.getElementById("registerForm");
const tokenValue = document.getElementById("tokenValue").value;

const uploadSize = 10000000;
let realSize = 0;
let size = 0;

let count = 1;

fileDropZone.addEventListener("dragenter", function(e) {
    e.preventDefault();
});

fileDropZone.addEventListener("dragover", function(e) {
    e.preventDefault();
});

fileDropZone.addEventListener("drop", function(e) {
    e.preventDefault();

    let files = e.dataTransfer.files;

    let file = files;

    let formData = new FormData();

    for(let i = 0; i < file.length; i++) {
    	if(i == 0) {
    		if(file[i].size > uploadSize) {
    			showModal('size over');
            	return;
    		}
    	}
    	
        if(size > uploadSize) {
            showModal('size over');
            return;
        } else {
            size += file[i].size
        }
    }

    if(size < uploadSize) {
        for(let i = 0; i < file.length; i++) {
            if(file[i].type.includes("image")) {
                formData.append("files", file[i]);
            } else {
                showModal('not image');
                return;
            }
        }
    }
	
    fetch('http://172.30.1.9:8080/files', {
        method: 'POST',
        body: formData,
        headers: {
	    	"X-CSRF-TOKEN": tokenValue,
	    }
    }).then(function(response) {
        return response.json();
    }).then(function(data) {
        
        data.files.forEach(list => {
        	let str = "";
        	
        	const child = document.createElement('div');
        	child.setAttribute("class", "col-sm-3");
        	child.setAttribute("id", "file" + count);
        	
            str += 	'<div class="card border-primary mb-3">';
            str += 		'<div class="card-header text-end">';
            str += 			'<i class="bi bi-x-circle-fill" data-src="' + list.file + '" onclick="deleteFile(\'' + list.file + '\', ' + count + ', ' + list.size + ')"></i>';
            str +=		'</div>';
            str += 		'<div class="card-body">';
            str += 			'<img src="/files/displayFile?fileName=' + list.file + '" class="card-img-bottom" />';
            str += 		'</div>';
            str += 	'</div>';
               
            child.innerHTML = str;
            
            document.querySelector(".uploadList").appendChild(child);
            
            
            realSize += list.size / 1048576;
            document.getElementById("sizeCheck").innerText = "????????? ?????? (" + realSize.toFixed(2) + " M)";
            count++;
        });

        
        

    }).catch(function(error) {
        console.log("error ----------->", error);
    });
    
});

function deleteFile(fileName, num, size) {
	
	fetch('http://172.30.1.9:8080/files/deleteFile', {
        method: 'POST',
        body: fileName,
        headers: {
	    	"X-CSRF-TOKEN": tokenValue,
	    }
    }).then(function(response) {
        return response.text();
    }).then(function(data) {
    	
    	realSize -= size / 1048576;
        
        document.getElementById("sizeCheck").innerText = "????????? ?????? (" + realSize.toFixed(2) + " M)";
        document.querySelector("#file" + num).remove();

    }).catch(function(error) {
        console.log("error ----------->", error);
    });
}

function showModal(kind) {
    const myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), '');

    myModal.show();

    document.getElementById('staticBackdropLabel').innerText = '??????';
    
    if(kind == 'not image') {
    	document.getElementById("modal-body").innerHTML = '????????? ????????? ?????? ???????????????.';
    } else {
    	document.getElementById("modal-body").innerHTML = '????????? ?????? ???????????????.(10M)';
    }
    
    if(kind == "title") {
    	document.getElementById("modal-body").innerHTML = '????????? ????????? ?????????.';
    }
    
    if(kind == "content") {
    	document.getElementById("modal-body").innerHTML = '????????? ????????? ?????????.';
    }
    
    document.getElementById("modalCommit").innerText = '??????';

    document.getElementById("modalClose").hidden = true;
}


form.addEventListener("submit", function(e) {
	console.log("submit");
	e.preventDefault();
	
	const imageField = document.querySelectorAll(".bi-x-circle-fill");
	
	const title = document.getElementById("title");
	const writer = document.getElementById("writer");
	const content = document.getElementById("content");
	
	
	let index = 0;
        	
	imageField.forEach(function(that) {
		const hiddenInput = document.createElement("input");
        const src = that.getAttribute("data-src");
        
        hiddenInput.setAttribute("type", "hidden");
    	hiddenInput.setAttribute("value", src);
    	hiddenInput.setAttribute("name", "files[" + index + "]");
        form.appendChild(hiddenInput);
        
        index++;
    })
    
    if(title.value == "") {
		showModal("title");
		return;
	} 
	
	if(writer.value == "") {
		return;
	}
	
	if(content.value == "") {
		showModal("content");
		return;
	}
    
    form.submit();
});