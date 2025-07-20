package uz.dev.rentcar.utils;

public class CommonUtils {

    public static<T> T getOrDef(T value, T def){
        return value == null ? def : value;
    }

//    public static User currentUser(){
//        Object principal = SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//
//        if(principal instanceof User currentUser){
//            return currentUser;
//        }
//        throw new SecurityException("Current user is anonymous");
//    }
}
