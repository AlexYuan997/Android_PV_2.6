package cn.rivamed.interfacemode;

public  class BaseClient {

    	
    public void	 getIp() {//获取IP
		}
    public void	 	setIp() {//修改IP
		}
    	


//    public abstract String RemoteAddress ();
    public String getRemoteAddress(){//获取远程地址
    	
    	
    	return new String();
    }
    public String setRemoteAddress(){//修改远程的地址
    	
    	
    	return new String();
    }




        Thread thread;//声明一个线程

        public boolean KeepRecive () {//检测设备是否还链接
			return false;
		}


        public BaseClient(TcpClient tcpClient)
        {
        	tcpClient.Client = tcpClient;
        	tcpClient.IP = ((System.Net.IPEndPoint)(Client.Client.RemoteEndPoint)).Address.ToString();
        	tcpClient.RemoteAddress = ((System.Net.IPEndPoint)(Client.Client.RemoteEndPoint)).ToString();
            thread = new Thread(ReciveDatas);
            thread.Start();
        }


		public   void ReciveDatas()    //复苏的数据
        {
//			BaseClient.  KeepRecive() = true;
        }


        protected void Send2Client(byte[] buf)        //发送协议
        {
//            this.Client.GetStream().Write(buf, 0, buf.Length);
        }

        public  void Release()         //释放支援
        {
//            KeepRecive = false;
//            Client.Client.Close();
//            this.OnDisconnected();
        }

//        public event EventHandler Disconnected;

        protected  void OnDisconnected()
        {
//            this.Disconnected?.Invoke(this, EventArgs.Empty);
        }

//        public event EventHandler<LogArgs> Logged;
        
        
//        protected void OnLogged(string log, bool error)
//        {
//            LogArgs e = new LogArgs(log, error);
//            this.Logged?.Invoke(this, e);
//        }
        
        
//        protected void OnLogged(String log)
//        {
//            LogArgs e = new LogArgs(log);
//            this.Logged?.Invoke(this, e);
//        }
    }

