import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ImportService } from '../../services/import.service';

@Component({
  selector: 'import-page',
  templateUrl: './import-page.component.html',
  styleUrls: ['./import-page.component.scss'],
})
export class ImportPageComponent implements OnInit {
  fileToUpload: File | any = null;
  invalidFile: boolean = false;

  constructor(
    private importService: ImportService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  import() {
    const formData = new FormData();
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
    this.importService.sendImportData(formData).subscribe(
      (data) => {
        this.invalidFile = false;
        this.fileToUpload = null;
        window.location.href = '/contacts';

        this.snackBar.open('Your contacts have been uploaded.', 'Close', {
          duration: 3000,
        });
      },
      (err) => {
        this.invalidFile = true;
      }
    );
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
