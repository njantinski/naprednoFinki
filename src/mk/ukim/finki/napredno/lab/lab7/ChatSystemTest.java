package mk.ukim.finki.napredno.lab.lab7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,params);
                    }
                }
            }
        }
    }

}


class ChatRoom {
    private String name;
    private Set<String> users;

    public ChatRoom(String name){
        this.name = name;
        this.users = new TreeSet<>();
    }

    public void addUser(String name){
        users.add(name);
    }

    public void removeUser(String name){
        users.remove(name);
    }

    public boolean hasUser(String name){
        return users.contains(name);
    }
    public int numUsers(){
        return users.size();
    }

    public String getName() {
        return name;
    }

    public Set<String> getUsers() {
        return users;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        String joining = String.join("\n",users);
        if(joining == ""){
            sb.append("EMPTY").append("\n");
        }
        else {
            sb.append(joining).append("\n");
        }
        return sb.toString();
    }
}


class ChatSystem{
    private Map<String, ChatRoom> chatRooms;

    public ChatSystem(){
        chatRooms = new TreeMap<>();
    }

    public void addRoom(String name){
        chatRooms.put(name,new ChatRoom(name));
    }

    public void removeRoom(String name){
        chatRooms.remove(name);
    }

    public ChatRoom getRoom(String name) throws NoSuchRoomException {
        if(!chatRooms.containsKey(name))
            throw new NoSuchRoomException(name);
        return chatRooms.get(name);
    }

    public void register(String name){
        ChatRoom first = chatRooms.values().stream().sorted(Comparator.comparing(ChatRoom::numUsers).thenComparing(ChatRoom::getName)).findFirst().orElse(null);
        if(first == null) return;
        first.addUser(name);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
        if(!chatRooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }
        if(chatRooms.values().stream().filter(set -> set.getUsers().contains(userName)).collect(Collectors.toSet()) == null){
            throw new NoSuchUserException(userName);
        }
        chatRooms.get(roomName).addUser(userName);
    }

    public void leaveRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if(!chatRooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }
        if(chatRooms.values().stream().filter(set -> set.getUsers().contains(userName)).collect(Collectors.toSet()) == null){
            throw new NoSuchUserException(userName);
        }
        chatRooms.get(roomName).removeUser(userName);
    }

    public void followFriend(String userName, String friendName) throws NoSuchUserException {
        if(chatRooms.values().stream().filter(set -> set.getUsers().contains(userName)).collect(Collectors.toSet()) == null)
            throw new NoSuchUserException(userName);
        if(chatRooms.values().stream().filter(set -> set.getUsers().contains(friendName)).collect(Collectors.toSet()) == null){
            throw new NoSuchUserException(userName);
        }
        chatRooms.values().stream().filter(room -> room.hasUser(friendName)).forEach(room -> room.addUser(userName));
    }

    public void registerAndJoin(String userName,String roomName){
        chatRooms.get(roomName).addUser(userName);
    }

}

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String name){
        super(String.format("Room %s doesn't exists!", name));
    }
}

class NoSuchUserException extends Exception{
    public NoSuchUserException(String name){
        super(String.format("User %s doesn't exists!", name));
    }
}