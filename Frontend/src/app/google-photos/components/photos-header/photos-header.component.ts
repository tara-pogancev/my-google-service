import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { User } from 'src/app/shared/model/user-model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user-service';
import { SearchParametersDTO } from '../../DTO/SearchParametersDTO';
import { PhotoService } from '../../services/photo.service';
import { SearchParametersService } from '../../services/search-parameters.service';
import { UploadService } from '../../services/upload.service';

@Component({
  selector: 'photos-header',
  templateUrl: './photos-header.component.html',
  styleUrls: ['./photos-header.component.scss']
})
export class PhotosHeaderComponent implements OnInit {
  user: User = new User();

  searchBarFocus = false
  searchValue: string = ""
  beforeDate!: Date | null
  afterDate!: Date | null
  constructor(private authService: AuthService,
     private userService: UserService,
      private photoService: PhotoService,
      private uploadService: UploadService,
      private searchParametersService: SearchParametersService) { }

  ngOnInit(): void {
    this.initUser()
  }

  initUser() {
    this.user.id = this.authService.getCurrentUser().id;
    this.userService.getCurrentUser().subscribe((data) => (this.user = data));
  }

  toggleSidebar() {

  }

  refreshPage() {

  }

  search() {
    let searchParameters: SearchParametersDTO = {
      searchValue: this.searchValue,
      afterDate: this.afterDate ? moment(this.afterDate, 'YYYY-MM-DD').format('DD-MM-YYYY') : '',
      beforeDate: this.beforeDate ? moment(this.beforeDate, 'YYYY-MM-DD').format('DD-MM-YYYY') : '',
      action: 'SEARCH'
    }
    this.searchParametersService.changeValue(searchParameters)
  }

  redirectContacts() {
    window.location.href = '/contacts';
  }

  redirectPhotos() {
    window.location.href = '/photos';
  }

  redirectEditProfile() {
    window.location.href = '/edit-profile';
  }

  signOut() {
    this.authService.logout();
  }

  fileSelectionChanged(event: Event)
{
    let selectedFiles:string[]  = [];

    const element = event.currentTarget as HTMLInputElement;
    if (!element || !element.files)
      return

    let selFiles = element.files;
    let fileList: FileList = element.files;
    if (fileList) {
      for (let itm in fileList)
      {
        let item: File = fileList[itm];
        if ((itm.match(/\d+/g) != null) && (!selectedFiles.includes(item['name'])))
            selectedFiles.push(item['name']);
      }
    }
    let formData = new FormData();
    formData.append('info', new Blob([JSON.stringify({email: this.authService.getCurrentUser().email})], {type: "application/json"}))
    if (selectedFiles.length)
    {
      for (let i=0 ; i < selectedFiles.length ; i++)
      {
        formData.append('files', selFiles[i],
           selFiles[i].name);
      }
    }
    this.photoService.postPhoto(formData).subscribe((data:any) => {
      this.uploadService.changeValue('UPLOAD')
    }, (err: Error) => {
       console.log(err)
    })
}


  resetSearch() {
    this.searchValue = ""
    this.afterDate = null
    this.beforeDate = null
    let searchParameters: SearchParametersDTO = {
      searchValue: this.searchValue,
      afterDate: this.afterDate ? moment(this.afterDate, 'YYYY-MM-DD').format('DD-MM-YYYY') : '',
      beforeDate: this.beforeDate ? moment(this.beforeDate, 'YYYY-MM-DD').format('DD-MM-YYYY') : '',
      action: 'SEARCH'
    }
    this.searchParametersService.changeValue(searchParameters)
  }

}
