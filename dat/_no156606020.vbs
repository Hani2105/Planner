Set objMessage = CreateObject("CDO.Message")
objMessage.Subject = "Csekme Krisztián eseménykezelõje "
objMessage.From = "krisztian.csekme1@sanmina.com"
objMessage.To = "roland.bognar@sanmina.com,krisztian.csekme1@sanmina.com,gabor.hanacsek@sanmina.com,viktor.feher@sanmina.com"
objMessage.cc = ""
objMessage.HTMLBody = "<HTML><BODY><html>  <head>      </head>  <body>    2015. Augusztus. 18, Kedd 14:36:00 &gt;S &#225;llom&#225;son: gy&#225;rt&#225;s indu&#237;t&#225;sa,     Jobsz&#225;m: HBSF1534230 | Partnumber: LFHB2197634-SMT | Mennyis&#233;g: 574 |     V&#225;rhat&#243; befejez&#233;s: 2015. Augusztus. 19, Szerda 02:57:00<br><br>  </body></html></BODY></HTML>"
objMessage.BodyPart.CharSet = "UTF-8"
objMessage.Configuration.Fields.Item _
   ("http://schemas.microsoft.com/cdo/configuration/sendusing") = 2
objMessage.Configuration.Fields.Item _
("http://schemas.microsoft.com/cdo/configuration/smtpserver") = "mailhub.sanmina.com"
objMessage.Configuration.Fields.Item _
("http://schemas.microsoft.com/cdo/configuration/smtpserverport") = 25
objMessage.Configuration.Fields.Update
objMessage.Send
