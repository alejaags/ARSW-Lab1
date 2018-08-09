/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 2110111
 */
public class HostBlackListThread extends Thread{
    
    private String ipaddress;
    private int N;
    private List<Integer> blackListOcurrences;
    
    public HostBlackListThread(String ipaddress, int N){
        this.ipaddress = ipaddress;
        this.N = N;
        HostBlackListsValidator hblv=new HostBlackListsValidator(); 
        blackListOcurrences=hblv.checkHost(ipaddress, N);
        System.out.println(hblv.Ocurrences());
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
    }
    
}
