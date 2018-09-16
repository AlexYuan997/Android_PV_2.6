package cn.rivamed.achive;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import cn.rivamed.interfacemode.BaseClient;



    public class Etv002Client extends BaseClient
    {

        boolean  fingerRegMode = false;

        boolean fingerBusy = false;

/*        public Etv002Client(TcpClient tcpClient) : base(tcpClient)
        {

        }*/

        public  void ReciveDatas(Reader reader)
        {
//            base.ReciveDatas();
//            using (BinaryReader reader = new BinaryReader(Client.GetStream()))
//            {
                while (KeepRecive())
                {
                    try
                    {

                        byte head = (byte) reader.read();
                        if (head != Etv002DataProtocol.BEGIN_FLAG)
                        {
                            continue;
                        }
                        byte len1 = (byte) reader.read();  //第一位
                        byte len2 = (byte) reader.read();

                        int len = len1 * 256 + len2;
//                        byte[] buf = reader.ReadBytes(len);
                        byte[] buf = new byte[len];

                        byte[] allbuf = new byte[buf.length + 3];
                        allbuf[0] = head;
                        allbuf[1] = len1;
                        allbuf[2] = len2;
           System.arraycopy(buf, 0, allbuf, 3, buf.length);
                        //计算校验码 
                        int check = Etv002DataProtocol.CheckSum(allbuf, 0, allbuf.length - 1);
//                        Console.WriteLine("原始数据={0},校验码={1:x2}", Transfer.Byte2String(allbuf), check);

//                        OnLogged("接收信息" + Transfer.Byte2String(allbuf));
                        if (allbuf[allbuf.length - 1] != check)
                        {
                            continue;
                        }
                        switch (allbuf[3])
                        {
                            case 0x00: //来自模块本身
                                ProcessModelMessage(allbuf);
                                break;
                            case 0x01://IO
                                ProcessIOData(allbuf);
                                break;
                            case 0x02://串口1
                                ProcessIdCard(allbuf);
                                break;
                            case 0x03: //串口2
                                ProcessFingerData(allbuf);
                                break;
                        }
                    }
                    catch (Exception ex)
                    {
                        this.Release();
                        break;
                    }
                }
            }
//        }  //fun end
     
        private void ProcessModelMessage(byte[] buffer)
        {
            switch (buffer[4] & 0xff)
            {
                case 0x67://心跳
                    ResponseHearBear(buffer);
                    break;
                case 0x68://读取模块id
                    ProcessDeviceId(buffer);
                    break;
                case 0x69://修改模块ID返回接口

                    ProcessResetId(buffer);
                    break;
            }
        }

        private void ProcessResetId(byte[] buf)
        {
            if (buf.length == 14)  //成功
            {
                byte[] bid = new byte[8];
        System.arraycopy(buf, 5, bid, 0, 8);
//                String sid = Transfer.Byte2String(bid);
//                OnLogged(String.Format(" ETV002 设备 {0} 设置ID 成功,ID ={1}", IP, sid));
            }
            else if (buf.length == 7)
            { //失败
//                OnLogged(String.Format(" ETV002 设备 {0} 设置ID失败", IP));
            }
        }

        public void SendGetId()
        {
            byte[] buf = new byte[] { 0x00, 0x68 };
            SendBuf(buf);
        }

        public void SendSetId(String id, byte[] idbuf )
        {
//            byte[] idbuf = Transfer.HexString2Byte(id);
            byte[] buf = new byte[10];
            buf[0] = 0x00;
            buf[1] = 0x69;
  System.arraycopy(idbuf, 0, buf, 2, idbuf.length);
            SendBuf(buf);
        }

        private void ResponseHearBear(byte[] buffer)
        {
            if (buffer.length > 0 && buffer[4] == 0x67 && buffer[5] == 0x05)
            {
                return;
            }
            byte[] buf = new byte[] { 0x00, 0x67, 0x01 };
            SendBuf(buf);
        }

        private void ProcessDeviceId(byte[] buf)
        {
            String id = "";
            if (buf != null)
            {
                if (buf.length == 14 && buf[4] == 0x68)
                {
                    byte[] bufId = new byte[8];
                    System.arraycopy(buf, 5, bufId, 0, 8);
//                    String deviceId = Transfer.Byte2String(bufId);
//                    OnLogged(String.Format("Etv002设备{0} 获取到设备ID={1}", IP, deviceId));
                }
            }
        }

        private void ProcessIOData(byte[] buf)
        {
            String s = "";
            switch (buf[4] & 0xff)
            {
                case 0x70://开锁
                    ProcessOpenDoor(buf);
                    break;
                case 0x71://关锁
                    ProcessDoorClosed(buf);
                    break;
                case 0x72://查看锁状态
                    ProcessDoorState(buf);
                    break;
                case Etv002DataProtocol.CMD_IO_OUTPUT_2_OPEN:
                    
//                   s = String.Format("ETV002 设备{0} IO2输出 OPEN  DATA={1}", IP, Transfer.Byte2String(buf));
               //     OnLogged(s);
                    break;
                case Etv002DataProtocol.CMD_IO_OUTPUT_2_CLOSE:
//                    s = String.Format("ETV002 设备{0} IO2输出 CLOSE   DATA={1}", IP, Transfer.Byte2String(buf));
                  //  OnLogged(s);
                    break;
                case Etv002DataProtocol.CMD_IO_INPUT_2_READ:
//                    s = String.Format("ETV002 设备{0} IO读取状态   DATA={1}", IP,Transfer.Byte2String(buf));
                //    OnLogged(s);
                    break;
            }
        }

        private void ProcessOpenDoor(byte[] buf)
        {
            //B00004017000DB
            if (buf != null)
            {
                boolean doorOpened = buf[5] == 0x01;
                if (buf.length == 7 && buf[4] == 0x70)
                {
                    String s = "";
                    switch (buf[5])
                    {
                        case 0x00:
//                            s = String.Format("ETV002 设备{0} 门锁开启失败", IP);
                            break;
                        case 0x01:
//                            s = String.Format("ETV002 设备{0} 门锁开启成功", IP);
                            break;
                        case 0x02:
//                            s = String.Format("ETV002 设备{0} 门锁已处于开启状态", IP);
                            break;
                    }

              //      OnLogged(s);
                }
            }
        }

        private void ProcessDoorClosed(byte[] buf)
        {
            //B00004017101D9
            if (buf != null)
            {
                boolean doorOpened = buf[5] == 0x01;
                if (buf.length == 7 && buf[4] == 0x71 && buf[5] == 0x01)
                {
//                    String s ="ETV002 设备{0} 门锁 {1}" +IP.doorOpened() ? "已关闭" : "未未关闭";
              //      OnLogged(s);
                }
            }
        }

        private void ProcessDoorState(byte[] buf)
        {
            if (buf != null)
            {
                if (buf.length == 7 && buf[4] == 0x72)
                {
                    boolean doorOpened = buf[5] == 0x01;
                //    String s = String.Format("ETV002 设备{0} 门锁{1}", IP, doorOpened ? "已打开" : "未打开");
               //     OnLogged(s);
                }
            }
        }

        private void ProcessIdCard(byte[] buf)
        {
            if (buf != null)
            {
                if (buf.length == 14)
                {
                    byte[] bufId = new byte[8];
            System.arraycopy(buf, 5, bufId, 0, 8);
//                    String id = Transfer.Byte2StringAscII(bufId);
//                    String log = String.format("ETV002设备 {0} 下位读卡器读卡结果 {1}", IP, id);
//                    OnLogged(log);
                }
            }
        }

        List<Byte> fingerData = new ArrayList<Byte>();
        int postion = 0;
        /**
         * 串口2 解析指纹仪数据
         */
        private void ProcessFingerData(byte[] buf)
        {
            boolean completed = false;
            boolean error = false;

            fingerBusy = false;
            //计算校验码
            int retCheck = Etv002DataProtocol.CheckSum(buf, 0, buf.length - 1);
            if (retCheck != (buf[buf.length - 1] & 0xff))
            {
                return;
            }

            if (!error)
            {
                /**
                 * evt002 协议；按照协议进行拆包
                 *
                 * head  |  len   | dst/src |cmd    |  data  | check
                 * 1byte |  2byte | 1byte   | 1byte | N byte | 1byte
                 *
                 * len = dst ~ check  包含 data,check
                 * */
                int len = buf[1] * 256 + buf[2];
                byte[] fingerOrg = new byte[len - 3];
                System.arraycopy(buf, 5, fingerOrg, 0, len - 3);

                /**
                 * 为保证数据能正常通过终端传输，所有上传、下发数据都拆解为2字节可见字符。
                 * 如 0x23 拆分为0x32 0x33,及高4位+0x30 低4位+0x30.
                 * 拆包时需要反其道而行之
                 *
                 * */
                byte[] finger = new byte[fingerOrg.length / 2];
                for (int i = 0, index = 0; i < fingerOrg.length && index < finger.length; i += 2, index++)
                {  //解析原始数据
                    int b = ((fingerOrg[i] % 0x10) * 0x10 + (fingerOrg[i + 1] % 0x10));
                    finger[index] = (byte)(b & 0xff);
                }

                /**
                 *
                 *  指纹协议 （返回数据）
                 *  头  0xEF01 | 	芯片地址  |	包标识 03/ 07/08  |	包长度（从确认码第1位开始计算）  |	确认码  |	返回参数 |	校验和
                 *  2  bytes      |    4bytes	    |   1  byte	                 | 2  byte                                               |   1  bytes |	N  bytes |	2  bytes
                 *
                 *  说明：返回包每包数据大小最大为128字节，
                 *  超过128字节的数据拆分为多包传输，
                 *  包标识：07表示返回结果，包标识 03代表还有后续包传输，包标识 08表示数据传输结束。
                 *  确认码：	00h：表示指令执行完毕或 OK 	01h：表示数据包接收错误
                 *  校验位：  命令 从包标识到参数最后1个字节计算   返回 从包标识到返回参数最后1个字节计算
                 *  当包标识为 02 或 08 时，没有确认码
                 * */

                //头部必须为 0xef 0x01
                if (((0xff & finger[0]) != 0xef) || (0xff & finger[1]) != 0x01)
                {
                    error = true;
                }

                //计算校验码
                int postion = 0;   //每个数据包的开始

                while (!error && postion < finger.length)
                {
                    len = (0xff & finger[postion + 7]) * 256 + (0xff & finger[postion + 8]);   //从len后开始计算，
                    byte[] check = Etv002DataProtocol.FingerCheckSum(finger, postion + 6, len - 2 + 3);
                    if (finger[postion + 9 + len - 2] != check[0] || finger[postion + 9 + len - 1] != check[1])
                    {  //确认码前有9个字节未计入len
                       //校验未通过
                        completed = true;
                        error = true;
                    }

                    if (finger[postion + 6] == 0x07)  //判断包标识  执行结果   0x07 表示执行结果，目前的指令中，07之后都还有数据
                    {
                        if ((0xff & finger[postion + 9]) != 0x00 && len == 3)
                        {
//                            OnLogged(String.format("ETV002设备 {0} 指纹采集超时", IP));
                            error = true;
                        }
                        else if (len > 3)
                        {  //有实际数据
                            byte[] bf = new byte[len - 3];
                            System.arraycopy(finger, postion + 10, bf, 0, bf.length);
                           
                        }
                    }
                    else if (finger[postion + 6] == 0x08)
                    {
                        byte[] bf = new byte[len - 2];
                    System.arraycopy(finger, postion + 9, bf, 0, bf.length);
                        completed = true;
                    }
                    else
                    {
                        byte[] bf = new byte[len - 2];
                       System.arraycopy(finger, postion + 9, bf, 0, bf.length);
       
                    }
                    postion = postion + 9 + len;
                }
                if (!error && completed)
                {   //无错误，并读取完成

//                    byte[] fingerSData = (byte[])fingerData.toArray();

//                    String fingStr = Transfer.byte2Base64StringFun(fingerSData);
                    if (fingerRegMode)
                    {
//                        String s = String.Format("ETV002 设备{0} 获取到指纹注册数据{1}", IP, fingStr);
//                        OnLogged(s);
                    }
                    else
                    {
//                        String s = String.Format("ETV002 设备{0} 获取到指纹采集数据{1}", IP, fingStr);
//                        OnLogged(s);
                    }
                }
            }
        }

        private void SendBuf(byte[] buf)
        {

            byte[] bufSend = Etv002DataProtocol.PieceCommond(buf);
            super.Send2Client(bufSend);
//            String s = Transfer.Byte2String(bufSend);
//            OnLogged("发送指令:" + s);
        }

        public void SendFingerRegister()
        {
            if (fingerBusy)
            {
//                OnLogged("指纹仪正在处于采集状态，请稍后", true);
                return;
            }
            byte[] buf = new byte[] { 0x03, 0x6d, 0x3e, 0x3f, 0x30, 0x31, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x30, 0x31, 0x30, 0x30, 0x30, 0x33, 0x32, 0x31, 0x30, 0x30, 0x32, 0x35 };
            fingerRegMode = true;
            SendBuf(buf);
//            OnLogged(String.format("etv002设备 {0} 已发送指纹注册命令，请连续按3次指纹或稍后", IP));
        }

        public void SendFingerGetImage()
        {
            if (fingerBusy)
            {
//                OnLogged("指纹仪正在处于采集状态，请稍后", true);
                return;
            }
            byte[] buf = new byte[] { 0x03, 0x6d, 0x3e, 0x3f, 0x30, 0x31, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x3f, 0x30, 0x31, 0x30, 0x30, 0x30, 0x33, 0x32, 0x32, 0x30, 0x30, 0x32, 0x36 };
            fingerRegMode = false;
            SendBuf(buf);
//            OnLogged(String.format("etv002设备 {0} 已发送指纹采集命令，请按下指纹或稍后", IP));
        }

        public void SendOutput1Open()
        {
            byte[] buf = new byte[] { 0x01,Etv002DataProtocol.CMD_IO_OUTPUT_1_OPEN };
            SendBuf(buf);
        }

        public void SendOutput1Close() {
            byte[] buf = new byte[] { 0x01, Etv002DataProtocol.CMD_IO_OUTPUT_1_CLOSE };
            SendBuf(buf);
        }

        public void SendOutput1Read() {
            byte[] buf = new byte[] { 0x01, Etv002DataProtocol.CMD_IO_OUTPUT_1_READ };
            SendBuf(buf); 
        }

        public void SendOutput2Open()
        {
            byte[] buf = new byte[] { 0x01, Etv002DataProtocol.CMD_IO_OUTPUT_2_OPEN };
            SendBuf(buf);
        }

        public void SendOutput2Close()
        {
            byte[] buf = new byte[] { 0x01, Etv002DataProtocol.CMD_IO_OUTPUT_2_CLOSE };
            SendBuf(buf);
        }

        public void SendOutput2Read()
        {
            byte[] buf = new byte[] { 0x01, Etv002DataProtocol.CMD_IO_OUTPUT_2_READ };
            SendBuf(buf);
        }

        public void SendIutput1Read()
        {
            byte[] buf = new byte[] { 0x01, Etv002DataProtocol.CMD_IO_INPUT_1_READ };
            SendBuf(buf);
        }

        public void SendIutput2Read()
        {
            byte[] buf = new byte[] { 0x01, Etv002DataProtocol.CMD_IO_INPUT_2_READ };
            SendBuf(buf);
        }



        /***
         * 发送开门指令，该指令存疑，是检测门锁状态，还是检测继电器状态
         */
        public void SendUnLock()
        {
            //todo 该指令存疑，是检测门锁状态，还是检测继电器状态
            byte[] buf = new byte[] { 0x01, 0x70 };
            SendBuf(buf);
        }

        /***
         * 发送检测门锁状态  IO 1
         */
        public void SendCheckLockState()
        {
            byte[] buf = new byte[] { 0x01, 0x72 };
            SendBuf(buf);
        }

		@Override
		public String RemoteAddress() {
			// TODO Auto-generated method stub
			return null;
		}
    }


