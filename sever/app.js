const express = require('express'),

  bodyParser = require('body-parser')
const mqtt = require('mqtt')
var mysql = require('mysql')
var client = mqtt.connect('mqtt://172.20.10.3:1884')
var connection = mysql.createConnection({
    host: '127.0.0.1',
    user: 'LZY',
    password: '970621',
    database: 'information'

})
var totalpeople =1
connection.connect();
const app = express()
 class QueueElement {

    constructor(element, priority,queuenumber) {

        this.element = element
        this.priority = priority
        this.queuenumber = queuenumber

    }

}


 class PriorityQueue {
    constructor() {

        this.items = []
    }
    enqueue(element, priority,queuenumber) {
        let queueElement = new QueueElement(element, priority,queuenumber)
        let added = false
        for (let i = 0; i < this.items.length; i++) {
            if (queueElement.priority < this.items[i].priority) {
                this.items.splice(i, 0, queueElement)
                added = true
                break
            }

        }
        if (!added) {

            this.items.push(queueElement)
        }
    }
    dequeue() {
        return this.items.shift()
    }
    isEmpty() {

        return this.items.length === 0
    }
    size() {

        return this.items.length
    }
    indexof(element){
        for(var i=0;i<this.items.length;i++){
            if(this.items[i].element==element)
                return i
        }
        return -1
    }
    delet(element){
        for(var i=0;i<this.items.length;i++){
            if(this.items[i].element==element)
                this.items.splice(i,1)
            return
        }

    }
}
 var QueuesmallTable = new PriorityQueue();
 var QueuemiddleTable = new PriorityQueue();
var QueuebigTable = new PriorityQueue();

client.on('connect',function () {
    client.subscribe('nextperson')
    console.log('Connect mqtt successflly')
    
})

client.on('message',function (topic ,message) {
    console.log('It demande ')
    let tabletype = message.toString()
    console.log(tabletype)
    switch (tabletype) {
        case "small":
            if(QueuesmallTable.isEmpty()){
                console.log(`there is no person\n`)
                client.publish('nextsmallpersonnumber','empty',null)
                break
            }
            let nextsmallnumber = QueuesmallTable.dequeue()
            let queuenumberwaite = nextsmallnumber.queuenumber
            console.log(`next person ${nextsmallnumber.queuenumber}`)
            client.publish("nextsmallpersonnumber",queuenumberwaite,null)
            break
        case"middle":
            if(QueuemiddleTable.isEmpty()){
                console.log(`there is no person\n`)
                client.publish('nextmiddlepersonnumber','empty',null)
                break
            }
            let nextmiddlenumber = QueuemiddleTable.dequeue()
            console.log(`next person ${nextmiddlenumber.queuenumber}`)
            client.publish('nextmiddlepersonnumber',nextmiddlenumber.queuenumber,null)
            break
        case 'big':
            if(QueuebigTable.isEmpty()){
                console.log(`there is no person\n`)
                client.publish('nextbigpersonnumber','empty',null)
                break
            }
            let nextbignumber = QueuebigTable.dequeue()
            console.log(`next person ${nextbignumber.queuenumber}`)
            client.publish("nextbigpersonnumber",nextbignumber.queuenumber,null)
            break




    }
    
})




app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*")
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
  res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
  next()
})


app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
  extended: true
}))

app.get('/table', (req, res, next) => {

  console.log('Demand table information\n')
    var tableinfo={'small':QueuesmallTable.size(),'middle':QueuemiddleTable.size(),'big':QueuebigTable.size()}
  return res.status(200).json({
    message: 'successfully',
    table: tableinfo
  })
})
app.get('/cancle',(req,res,next)=>{
  const type = req.query.type
    const username = req.query.name
  console.log(`cancle the table ${type}   the usernmae:  ${username}\n`)
  switch (type) {
      case'small':
          QueuesmallTable.delet(username)
          console.log(`small table cancle successflly  \n `)
          break
      case 'middle':
          QueuemiddleTable.delet(username)
          console.log(`middle table cancle successflly  \n `)
          break
      case 'big':
          QueuebigTable.delet(username)
          console.log(`big table cancle successflly  \n `)
          break
  }
  return res.status(200).json({
    message : 'successfully'
  })

})



app.post('/login', (req, res, next) => {
  const username = req.body.username,
      password1 = req.body.password
  console.log(`Login progressing \n `)
    if (!username || !password1) {
    console.log('Inconnect username or password\n')
    return res.status(200).json({
      message: 'It should not be empty',
      level : 'D'
    })
  }



  var arr1 = username.toString().split("")
    var arr2v = password1.toString().split("")

    for(var i=0;i<arr1.length;i++){
        if(arr1[i]=="="||arr1[i]=="'"){
            return res.status(200).json({
                message: 'Illegal character',
                level : 'D'
            })


        }
    }


    var sql = "SELECT level FROM clientinfo where name = ? and password = ? and islogin =?"
    connection.query(sql,[`${username}`,`${password1}`,'NO'],function (erro ,result) {
      if(erro){
          console.log(erro)
          return
      }

      if(result.length==0){
          console.log('Inconnect username or password\n')
          return res.status(200).json({
              message: 'Error！',
              level: 'D'
          })
      }
      else {
          console.log(`Login successfully \n name: ${username}  password:   ${password1}\n`)
          return res.status(200).json({
              message: 'Correct',
              level: result[0].level
          })
      }
   })
    return



})
app.post('/getnumber',(req,res,next)=>{
  const level = req.body.level
   const   tabletype = req.body.tabletype
    const username = req.body.username;
    console.log(`the client choses the type of the table ${tabletype}  \n`)

    console.log(`${username} want to get queue number\n`)
    var waitenumberreal
    switch (tabletype) {
        case 'small':
            QueuesmallTable.enqueue(username,level,(level+totalpeople))
            waitenumberreal = QueuesmallTable.indexof(username)
            console.log('small table enqueue successflly ')
            break
        case 'middle':
            QueuemiddleTable.enqueue(username,level,(level+totalpeople))
            waitenumberreal =QueuemiddleTable.indexof(username)

            console.log('middle table enqueue successflly ')
            break
        case 'big':
            QueuebigTable.enqueue(username,level,(level+totalpeople))
            waitenumberreal = QueuebigTable.indexof(username)

            console.log('big table enqueue successflly ')
            break
  }
  var re = level+totalpeople
  console.log(`the queue number ${re}\n`)
    var wait = totalpeople
    totalpeople++
    return res.status(200).json({
    queuenumber: level+wait,
    waitnumber : waitenumberreal


  })
})
app.listen(11111)

