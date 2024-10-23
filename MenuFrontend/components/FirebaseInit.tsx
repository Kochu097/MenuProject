import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
    apiKey: "AIzaSyCspa7VWILI5zy3H51T7xN6D6HGEb7rZfA",
    authDomain: "menutestproject.firebaseapp.com",
    projectId: "menutestproject",
    storageBucket: "menutestproject.appspot.com",
    messagingSenderId: "1042797842611",
    appId: "1:1042797842611:web:08479878038e8ac92ca0c6",
    measurementId: "G-BXEWB3VL1F"
  };
  
  
  const app = initializeApp(firebaseConfig);
  const auth = getAuth(app);
  export default auth;