import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { CreateUser } from 'src/app/shared/model/create-user';
import { AuthService } from 'src/app/shared/services/auth.service';
import { FavoritePhotoDTO } from '../DTO/FavoritePhotoDTO';
import { UpdatePhotoMetadataDTO } from '../DTO/UpdatePhotoMetadataDTO';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {

  constructor(private _http: HttpClient, private authService: AuthService) {}

  getAllPhotos(userId: number, favorites: boolean = false) {
    let params = new HttpParams();
    params = params.append('favorites', favorites)
    const headers = this.authService.getHeaders();
    return this._http.get(`${server}photos/users/${userId}`, {
      headers: headers,
      params: params
    });
  }
  rotatePhoto(filename: string) {
    const headers = this.authService.getHeaders();
    return this._http.put(`${server}photos/${filename}/rotate`,{}, {
      headers: headers
    });
  }
  updatePhotoMetadata(filename: string, updatePhotoMetadataDTO: UpdatePhotoMetadataDTO) {
    const headers = this.authService.getHeaders();
    return this._http.put(`${server}photos/${filename}/metadata`, updatePhotoMetadataDTO, {
      headers: headers
    });
  }
  favoritePhoto(filename: string, favoritePhotoDTO: FavoritePhotoDTO) {
    const headers = this.authService.getHeaders();
    return this._http.put(`${server}photos/${filename}/favorite`, favoritePhotoDTO, {
      headers: headers
    });
  }
  postPhoto(formData: FormData) {
    return this._http.post(`${server}photos`, formData)
  }

  getPhoto(filename: string) {
    const headers = this.authService.getHeaders();
    return this._http.get(`${server}photos/${filename}`, {
      headers: headers
    });
  }

  deletePhoto(filename: string) {
    const headers = this.authService.getHeaders();
    return this._http.delete(`${server}photos/${filename}`, {
      headers: headers
    });
  }

  getPhotoThumbnail(filename: string) {
    let jwt = this.authService.getCurrentUser().jwt
    const headers = new HttpHeaders({
      Authorization: `Bearer ` + jwt
    });
    return this._http.get(`${server}photos/${filename}/thumbnail`, {
      headers: headers,
      responseType: 'blob'
    });
  }

  getStorage(userId: number) {
    const headers = this.authService.getHeaders();
    return this._http.get(`${server}photos/users/${userId}/storage`, {
      headers: headers
    });
  }

  getExport(userId: number, fileNames:string[] = []) {
    const headers = this.authService.getHeaders();
    let params = new HttpParams();
    if (fileNames.length > 0) {
      fileNames.forEach(i => (params = params.append('fileNames', i)))
    }

    return this._http.get(`${server}photos/users/${userId}/export`, {
      headers: headers,
      responseType: 'arraybuffer',
      params: params
    });
  }

}
