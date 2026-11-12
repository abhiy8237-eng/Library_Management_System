const api=(p,o={})=>fetch("/api"+p,{headers:{"Content-Type":"application/json"},...o}).then(async r=>{if(!r.ok)throw new Error(await r.text()||"Request failed");return r.status===204?null:r.json()});
const $=id=>document.getElementById(id), esc=s=>String(s??"").replace(/[&<>"']/g,c=>({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
let books=[],members=[],loans=[];
const note=m=>$("notice").textContent=m;
async function refresh(){try{[books,members,loans]=await Promise.all([api("/books"),api("/members"),api("/loans/active")]);$("bookCount").textContent=books.reduce((n,b)=>n+b.totalCopies,0);$("memberCount").textContent=members.length;$("loanCount").textContent=loans.length;render();}catch(e){note(e.message)}}
function render(){let q=$("search").value.toLowerCase();let list=books.filter(b=>(b.title+" "+b.author).toLowerCase().includes(q));$("booksBody").innerHTML=list.map(b=>`<tr><td><b>${esc(b.title)}</b><br><small>${esc(b.isbn||"No ISBN")}</small></td><td>${esc(b.author)}</td><td>${esc(b.category||"—")}</td><td>${b.availableCopies}/${b.totalCopies}</td><td><button class="small" onclick="deleteBook(${b.id})">Delete</button></td></tr>`).join("")||'<tr><td colspan="5">No books found.</td></tr>';
$("membersList").innerHTML=members.map(m=>`<li><span>${esc(m.fullName)}<br>${esc(m.email||"")}</span><b>#${m.id}</b></li>`).join("")||"<li>No members yet.</li>";
$("loansList").innerHTML=loans.map(l=>`<li><span>${esc(l.book.title)}<br><small>${esc(l.member.fullName)} · Due ${l.dueDate}</small></span><button class="small" onclick="returnBook(${l.id})">Return</button></li>`).join("")||"<li>No active loans.</li>";
$("bookSelect").innerHTML='<option value="">Select a book</option>'+books.filter(b=>b.availableCopies>0).map(b=>`<option value="${b.id}">${esc(b.title)} (${b.availableCopies})</option>`).join("");
$("memberSelect").innerHTML='<option value="">Select a member</option>'+members.map(m=>`<option value="${m.id}">${esc(m.fullName)}</option>`).join("");}
$("search").addEventListener("input",render);
$("bookForm").addEventListener("submit",async e=>{e.preventDefault();let d=Object.fromEntries(new FormData(e.target));d.totalCopies=Number(d.totalCopies);try{await api("/books",{method:"POST",body:JSON.stringify(d)});e.target.reset();e.target.totalCopies.value=1;note("Book added.");await refresh()}catch(x){note(x.message)}});
$("memberForm").addEventListener("submit",async e=>{e.preventDefault();let d=Object.fromEntries(new FormData(e.target));try{await api("/members",{method:"POST",body:JSON.stringify(d)});e.target.reset();note("Member registered.");await refresh()}catch(x){note(x.message)}});
$("issueForm").addEventListener("submit",async e=>{e.preventDefault();let d=Object.fromEntries(new FormData(e.target));d.bookId=Number(d.bookId);d.memberId=Number(d.memberId);try{await api("/loans/issue",{method:"POST",body:JSON.stringify(d)});e.target.reset();note("Book issued for 14 days.");await refresh()}catch(x){note(x.message)}});
async function deleteBook(id){if(!confirm("Delete this book?"))return;try{await api("/books/"+id,{method:"DELETE"});note("Book deleted.");await refresh()}catch(e){note(e.message)}}
async function returnBook(id){try{await api("/loans/"+id+"/return",{method:"POST"});note("Book returned.");await refresh()}catch(e){note(e.message)}}
refresh();
