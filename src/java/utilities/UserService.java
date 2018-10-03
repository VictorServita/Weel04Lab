/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

public class UserService
{

    public UserService(){}
    
    public User login(String n, String p)
    {
        if(n.equals("Adam") && p.equals("password"))
        {
           User u = new User(n,p);
           return u;
        }
        return null;
    }
    
}
