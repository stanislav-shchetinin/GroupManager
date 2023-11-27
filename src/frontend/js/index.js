const http = require('http')
const fs = require('fs')
const path = require('path')
const commonPath = __dirname.substring(0, __dirname.length - 2);
export const URL_BACKEND = 'localhost:8080'

const server = http.createServer((req, res) => {

    let ext = path.extname(req.url.toString());
    let contentType;
    let filePath = '';
    console.log(ext);
    console.log(req.url.toString())

    if (ext === '.html' || !ext){
        contentType = 'text/html';
        filePath = path.join(commonPath, 'public', (req.url === '/' ? 'index.html' : req.url) + (!ext ? '.html' : ''));
    } else if (ext === '.css'){
        contentType = 'text/css';
        filePath = path.join(commonPath, req.url);
    } else if (ext === '.js'){
        contentType = 'text/js';
        filePath = path.join(commonPath, req.url);
    }

    console.log(filePath)

    fs.readFile(filePath, (err, content) => {
        if (err) {
            res.writeHead(404)
            res.end('Oh no. Status code: 404 (Not found)');
        } else {
            res.writeHead(200, {
                'Content-Type': contentType
            })
            res.end(content)
        }
    })

})

const PORT = 3000
server.listen(PORT, () => {
    console.log(`Server has been started on ${PORT}...`)
})