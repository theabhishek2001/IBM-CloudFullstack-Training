// function greet(){
//     console.log("Hy , good after noon");
// }

// // greet();

// [1,2,4].forEach((e) => {
//     console.log(e); 
// })


// function start(func){
//     func();
// }
// start(greet);
// console.log("Dose");
// const c= setTimeout(()=>{
//     console.log("Honey singh");
// },2000);
// clearTimeout(c)
// console.log("hero")


// let count=0;

// let d= setInterval(()=>{
//     console.log("Aaj humne Js padha");
//     count++;
//     if(count>10){
//         clearTimeout(d);
//     }
// },1000)



function getData(){
    return new Promise((resolve, reject) => {
        // if(resolve){
        //     console.log("Data Recieved");
        // }

        setTimeout(() => {
            if(1!=1){
                resolve("Data Recieved");
            }
            else{
                reject("Data not recieved");
            }
        }, 2000);
    })
}

getData()