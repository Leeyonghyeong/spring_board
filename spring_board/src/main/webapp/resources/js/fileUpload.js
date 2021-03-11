const fileDropZone = document.querySelector(".dropZone");
const fileDelete = document.querySelector(".bi-x-circle-fill");
const form = document.getElementById("registerForm");

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
            document.getElementById("sizeCheck").innerText = "업로드 용량 (" + realSize.toFixed(2) + " M)";
            count++;
        });

        
        

    }).catch(function(error) {
        console.log("error ----------->", error);
    });
    
});

function deleteFile(fileName, num, size) {
	
	fetch('http://172.30.1.9:8080/files/deleteFile', {
        method: 'POST',
        body: fileName
    }).then(function(response) {
        return response.text();
    }).then(function(data) {
    	
    	realSize -= size / 1048576;
        
        document.getElementById("sizeCheck").innerText = "업로드 용량 (" + realSize.toFixed(2) + " M)";
        document.querySelector("#file" + num).remove();

    }).catch(function(error) {
        console.log("error ----------->", error);
    });
}

function showModal(kind) {
    const myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), '');

    myModal.show();

    document.getElementById('staticBackdropLabel').innerText = '알림';
    
    if(kind == 'not image') {
    	document.getElementById("modal-body").innerHTML = '이미지 파일만 등록 가능합니다.';
    } else {
    	document.getElementById("modal-body").innerHTML = '용량이 초과 되었습니다.(10M)';
    }
    
    document.getElementById("modalCommit").innerText = '확인';

    document.getElementById("modalClose").hidden = true;
}


form.addEventListener("submit", function(e) {
	console.log("submit");
	e.preventDefault();
	
	const imageField = document.querySelectorAll(".bi-x-circle-fill");
	
	
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
    
    form.submit();
});