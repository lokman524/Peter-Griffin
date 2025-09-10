import { Spinner } from "@material-tailwind/react";

function Loading (){
    return (
        <div className="flex flex-col items-center h-screen justify-center">
            <p className="text-black text-center my-5 text-3xl">Loading...</p>
            <Spinner className="h-16 w-16 text-gray-900/50 animate-spin" color=""/>
        </div>
    );
}

export default Loading;