// console.log("Hy there ");

// for(let i=0;i<5;i++){
//     console.log(i);
// }

// j=0;
// do{
//     j++;
// }while(j<7);

// console.log(j);

// let l=5;
// var v=6;

// {
//     l=10;
//     v=20;
//     console.log(l)
//     console.log(v)
// }

// console.log(l)
// console.log(v)

let cal = {
    sum: (...e) => {
        let sum=0;
        return e.forEach(a=>{
            sum+=a;
        });
    }
}

console.log(cal.sum())