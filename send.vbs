'    Copyright (C) <2016> <Krisztián Csekme>
'    This program is free software: you can redistribute it and/or modify
'    it under the terms of the GNU General Public License as published by
'    the Free Software Foundation, either version 3 of the License, or
'    (at your option) any later version.
'
'    This program is distributed in the hope that it will be useful,
'    but WITHOUT ANY WARRANTY; without even the implied warranty of
'    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
'    GNU General Public License for more details.
'
'    You should have received a copy of the GNU General Public License
'    along with this program.  If not, see <http://www.gnu.org/licenses/>.
	
Dim WshShell, strCurDir
Set WshShell = CreateObject("WScript.Shell")
strCurDir    = WshShell.CurrentDirectory	

Dim fso
Set fso = CreateObject("Scripting.FileSystemObject")
Set file_subject = fso.OpenTextFile(strCurDir & "\subject.txt", 1)

Set file_from = fso.OpenTextFile(strCurDir & "\from.txt", 1)
Set file_to = fso.OpenTextFile(strCurDir & "\to.txt", 1)
Set file_message = fso.OpenTextFile(strCurDir & "\message.txt", 1)

content_subject = file_subject.ReadAll
content_from = file_from.ReadAll
content_to = file_to.ReadAll
content_message = file_message.ReadAll

Set objMessage = CreateObject("CDO.Message")
objMessage.Subject = content_subject
objMessage.From = content_from
objMessage.To = content_to
objMessage.cc = ""
objMessage.HTMLBody = content_message 
objMessage.BodyPart.CharSet = "UTF-8"
objMessage.Configuration.Fields.Item _
   ("http://schemas.microsoft.com/cdo/configuration/sendusing") = 2
objMessage.Configuration.Fields.Item _
("http://schemas.microsoft.com/cdo/configuration/smtpserver") = "mailhub.sanmina.com"
objMessage.Configuration.Fields.Item _
("http://schemas.microsoft.com/cdo/configuration/smtpserverport") = 25
objMessage.Configuration.Fields.Update
objMessage.Send