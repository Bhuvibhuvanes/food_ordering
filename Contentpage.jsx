import React from 'react'
import Image from '../imge/image';
import i from "../../assets/three.png"
 const ContentPage = () => {
   return (
    <>
    <div className="grid grid-cols-2 relative bg-[#00242B] gap-10 px-20 py-10 items-center "> 
         <div className=" pt-5  flex flex-col gap-5"> 
           <div>
             <h1 className="text-[#FF3E56] text-6xl font-semibold">Be The Fastest</h1>
        <h1 className="text-white text-6xl font-semibold">In Delivering</h1>
        <h1 className="text-white text-6xl font-semibold">Your Food</h1>
           </div>
        
        <p className="text-white">Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquid, quas odit. Ea fuga delectus accusantium.</p>
        <input type="text" placeholder="Search" class="placeholder-gray-400 text-gray-700 border border-gray-300 rounded-2xl py-2 px-4 focus:outline-none focus:ring-blue-500 focus:border-blue-500 h-8 w-60" />


         
      </div>
     
    <div><Image className="float-end h-6 flex-row absolute " imgSrc={i} /></div>
      </div>
    </>
   )   
}

export  default ContentPage ;
